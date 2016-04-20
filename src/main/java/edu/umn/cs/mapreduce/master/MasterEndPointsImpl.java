package edu.umn.cs.mapreduce.master;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.util.concurrent.*;
import edu.umn.cs.mapreduce.*;
import edu.umn.cs.mapreduce.common.Constants;
import edu.umn.cs.mapreduce.common.Utilities;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by jayapriya on 3/1/16.
 */
public class MasterEndPointsImpl implements MasterEndPoints.Iface {
    private static final Logger LOG = LoggerFactory.getLogger(MasterEndPointsImpl.class);

    private Set<String> liveNodes;
    private Map<String, Stopwatch> heartbeatMap;
    private int chunkSize;
    private int mergeBatchSize;
    private int taskRedundancy;
    private int heartbeatInterval;
    private JoinResponse joinResponse;
    private ListeningExecutorService listeningExecutorService;
    private JobStats jobStats;
    private Set<FileSplit> sortJobs;

    public MasterEndPointsImpl(int chunkSize, int mergeFilesBatchSize, int heartbeatInterval, int taskRedundancy,
                               double failProbability) {
        this.liveNodes = new HashSet<String>();
        this.heartbeatMap = new HashMap<String, Stopwatch>();
        this.chunkSize = chunkSize;
        this.mergeBatchSize = mergeFilesBatchSize;
        this.heartbeatInterval = heartbeatInterval;
        this.taskRedundancy = taskRedundancy;
        this.joinResponse = new JoinResponse(failProbability, heartbeatInterval);
        this.listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(100));
        this.sortJobs = new HashSet<FileSplit>();
    }

    @Override
    public JobResponse submit(JobRequest request) throws TException {
        long start = System.currentTimeMillis();
        this.jobStats = new JobStats();
        if (liveNodes.isEmpty()) {
            JobResponse response = new JobResponse(JobStatus.NO_NODES_IN_CLUSTER);
            LOG.info("No nodes in cluster. Returning response: " + response);
            return response;
        }

        LOG.info("Processing request: " + request);
        List<FileSplit> fileSplits = null;
        try {
            fileSplits = computeSplits(request.getInputFile(), chunkSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        jobStats.setChunkSize(chunkSize);
        jobStats.setNumSplits(fileSplits.size());
        LOG.info("Input File: {} Chunk Size: {} Num Splits: {}", request.getInputFile(),
                chunkSize, fileSplits.size());

        jobStats.setTotalSortTasks(fileSplits.size());
        sortJobs.addAll(fileSplits);
        CountDownLatch sortCountDownLatch = new CountDownLatch(fileSplits.size());
        List<SortResponse> sortResponses = scheduleSortJobs(fileSplits, sortCountDownLatch);
        if (sortResponses == null) {
            JobResponse response = new JobResponse(JobStatus.NO_NODES_IN_CLUSTER);
            LOG.info("No nodes in cluster. Returning response: " + response);
            return response;
        }
        jobStats.setTotalSuccessfulSortTasks(sortResponses.size());

        MergeResponse finalMerge = batchAndScheduleMergeJobs(sortResponses, mergeBatchSize);
        if (finalMerge == null) {
            JobResponse response = new JobResponse(JobStatus.NO_NODES_IN_CLUSTER);
            LOG.info("No nodes in cluster. Returning response: " + response);
            return response;
        }
        LOG.info("Final merge response: " + finalMerge);

        String outputFile = moveFileToDestination(finalMerge.getIntermediateFilePath());

        Utilities.deleteAllIntermediateFiles();

        JobResponse response = new JobResponse(JobStatus.SUCCESS);
        long end = System.currentTimeMillis();
        response.setOutputFile(outputFile);
        response.setExecutionTime(end - start);
        response.setJobStats(jobStats);
        LOG.info("Job finished successfully in " + (end - start) + " ms. Sending response: " + response);
        return response;
    }

    private String moveFileToDestination(String intermediateFilePath) {
        File outDir = new File(Constants.DEFAULT_OUTPUT_DIR);
        if (!outDir.exists()) {
            outDir.mkdir();
        }
        File outPath = new File(outDir + "/output_sorted");
        File intFile = new File(intermediateFilePath);
        try {
            Files.move(intFile, outPath);
            LOG.info("Copied file from " + intFile + " to " + outPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outPath.toString();
    }

    private MergeResponse batchAndScheduleMergeJobs(List<SortResponse> sortResponses, int mergeBatchSize) {
        List<String> filesToMerge = getAllFilesToMerge(sortResponses);
        MergeResponse finalMergeResponse = batchMergeJobs(filesToMerge, mergeBatchSize);
        return finalMergeResponse;
    }

    private MergeResponse batchMergeJobs(List<String> filesToMerge, int mergeBatchSize) {
        List<List<String>> batches = batchJobs(filesToMerge, mergeBatchSize);

        // since this could be recursive account for already set merge task count as well
        int totalMergeAlready = jobStats.getTotalMergeTasks();
        totalMergeAlready += batches.size();
        jobStats.setTotalMergeTasks(totalMergeAlready);

        LOG.info("Total files to merge: {} batchSize: {} numBatches: {}", filesToMerge.size(), mergeBatchSize,
                batches.size());

        List<MergeResponse> mergeResponses = scheduleMergeJobs(batches);

        if (mergeResponses == null) {
            return null;
        }

        MergeResponse finalMergeResponse;
        if (mergeResponses.size() == 1) {
            finalMergeResponse = mergeResponses.get(0);
        } else {
            List<String> nextFilesToMerge = getRemainingFilesToMerge(mergeResponses);
            finalMergeResponse = batchMergeJobs(nextFilesToMerge, mergeBatchSize);
        }
        return finalMergeResponse;
    }

    public List<List<String>> batchJobs(List<String> filesToMerge, int mergeBatchSize) {
        return Lists.partition(filesToMerge, mergeBatchSize);
    }

    private List<MergeResponse> scheduleMergeJobs(List<List<String>> mergeBatches) {
        // round robin assignment of merge jobs to all liveNodes
        Iterator<String> circularIterator = Iterables.cycle(liveNodes).iterator();
        List<Future<MergeResponse>> futureMergeResponses = new ArrayList<Future<MergeResponse>>();
        for (List<String> mergeBatch : mergeBatches) {
            String hostInfo = circularIterator.next();
            while (!isAlive(hostInfo)) {
                LOG.info(hostInfo + " is not alive. Removing from live node list.");
                circularIterator.remove();
                if (liveNodes.isEmpty()) {
                    return null;
                }
                hostInfo = circularIterator.next();
            }

            LOG.info(hostInfo + " is alive. Scheduling merge batch: " + mergeBatch + " for MERGE");
            String[] tokens = hostInfo.split(":");
            Future<MergeResponse> future = listeningExecutorService.submit(new MergeRequestThread(tokens[0],
                    Integer.parseInt(tokens[1]), mergeBatch));
            futureMergeResponses.add(future);
        }

        LOG.info("Submitted all merge requests. #requests: " + mergeBatches.size());
        List<MergeResponse> mergeResponses = new ArrayList<MergeResponse>();
        List<List<String>> failedBatches = new ArrayList<List<String>>();
        int index = 0;
        for (Future<MergeResponse> future : futureMergeResponses) {
            try {
                MergeResponse mergeResponse = future.get();
                if (mergeResponse.getStatus().equals(Status.SUCCESS)) {
                    mergeResponses.add(future.get());

                    // since this could be recursive account for already set successful merge task count as well
                    int totalSuccessAlready = jobStats.getTotalSuccessfulMergeTasks();
                    totalSuccessAlready += 1;
                    jobStats.setTotalSuccessfulMergeTasks(totalSuccessAlready);

                } else {
                    // failed responses means the corresponding requests has to be rescheduled for execution
                    List<String> failedBatch = mergeBatches.get(index);
                    failedBatches.add(failedBatch);
                }
            } catch (Exception e) {
                // considered failed
                failedBatches.add(mergeBatches.get(index));
            }
            index++;
        }

        // if there are any failed responses then reschedule those requests again
        if (!failedBatches.isEmpty()) {
            // since this could be recursive account for already set successful merge task count as well
            int totalFailedAlready = jobStats.getTotalFailedMergeTasks();
            totalFailedAlready += failedBatches.size();
            jobStats.setTotalFailedMergeTasks(totalFailedAlready);

            LOG.info(failedBatches.size() + " merge requests FAILED. Rescheduling the failed requests..");
            List<MergeResponse> responsesForFailedTasks = scheduleMergeJobs(failedBatches);
            if (responsesForFailedTasks == null) {
                return null;
            }
            mergeResponses.addAll(responsesForFailedTasks);
        }

        long totalMergeTime = 0;
        for (MergeResponse mergeResponse : mergeResponses) {
            totalMergeTime += mergeResponse.getExecutionTime();
        }
        long avgMergeTime = totalMergeTime / mergeResponses.size();
        jobStats.setAverageTimeToMerge(avgMergeTime);

        LOG.info("Got all merge responses back. #responses: " + mergeResponses.size());
        return mergeResponses;
    }

    private List<String> getAllFilesToMerge(List<SortResponse> sortResponses) {
        List<String> result = new ArrayList<String>();
        for (SortResponse sortResponse : sortResponses) {
            result.add(sortResponse.getIntermediateFilePath());
        }
        return result;
    }

    private List<String> getRemainingFilesToMerge(List<MergeResponse> mergeResponses) {
        List<String> result = new ArrayList<String>();
        for (MergeResponse mergeResponse : mergeResponses) {
            result.add(mergeResponse.getIntermediateFilePath());
        }
        return result;
    }

    private List<SortResponse> scheduleSortJobs(List<FileSplit> fileSplits, CountDownLatch sortCountDownLatch) {
        // round robin assignment of sort jobs to all liveNodes
        Iterator<String> circularIterator = Iterables.cycle(liveNodes).iterator();
        List<FileSplit> failedSplits = new ArrayList<FileSplit>();
        List<SortResponse> sortResponses = new ArrayList<SortResponse>();
        for (int i = 0; i < taskRedundancy; i++) {
            for (FileSplit fileSplit : fileSplits) {
                String hostInfo = circularIterator.next();
                while (!isAlive(hostInfo)) {
                    LOG.info(hostInfo + " is not alive. Removing from live node list.");
                    circularIterator.remove();
                    if (liveNodes.isEmpty()) {
                        return null;
                    }
                    hostInfo = circularIterator.next();
                }

                LOG.info(hostInfo + " is alive. Scheduling file split: " + fileSplit + " for SORT");
                String[] tokens = hostInfo.split(":");
                ListenableFuture<SortResponse> future = listeningExecutorService.submit(new SortRequestThread(tokens[0],
                        Integer.parseInt(tokens[1]), fileSplit));
                Futures.addCallback(future, new SortResponseListener(fileSplit, sortCountDownLatch, sortResponses, failedSplits, sortJobs, tokens[0],
                        Integer.parseInt(tokens[1])));
            }
        }
        LOG.info("Submitted all sort requests. #requests: " + fileSplits.size() + " #taskRedundancy: " + taskRedundancy);

        try {
            sortCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!failedSplits.isEmpty()) {
            // since this can be recursive, account for previous failed sort tasks as well
            int failedAlready = jobStats.getTotalFailedSortTasks();
            failedAlready += failedSplits.size();
            jobStats.setTotalFailedSortTasks(failedAlready);

            LOG.info(failedSplits.size() + " sort requests FAILED. Rescheduling the failed requests..");
            List<SortResponse> responsesForFailedTasks = scheduleSortJobs(failedSplits, sortCountDownLatch);
            if (responsesForFailedTasks == null) {
                return null;
            }
            sortResponses.addAll(responsesForFailedTasks);
        }

        long totalSortTime = 0;
        for (SortResponse sortResponse : sortResponses) {
            totalSortTime += sortResponse.getExecutionTime();
        }
        long avgSortTime = totalSortTime / sortResponses.size();
        jobStats.setAverageTimeToSort(avgSortTime);

        LOG.info("Got all sort responses back. #responses: " + sortResponses.size());
        return sortResponses;
    }

    private boolean isAlive(String hostInfo) {
        Stopwatch stopwatch = heartbeatMap.get(hostInfo);
        long elapsedTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        // if elapsed time of this node's stopwatch is greater than that of heartbeat interval, let's assume the
        // node to be dead and not schedule any tasks to it
        if (elapsedTime > 2 * heartbeatInterval) {
            LOG.info("Stopwatch elapsed time: {} ms for node: {} is greater than heartbeat interval: {}",
                    elapsedTime, hostInfo, heartbeatInterval);
            LOG.info("Assuming host: {} to be DEAD", hostInfo);
            return false;
        }
        return true;
    }

    public List<FileSplit> computeSplits(String inputFilename, long chunkSize) throws IOException {
        List<FileSplit> splits = new ArrayList<FileSplit>();
        File inputFile = new File(inputFilename);
        long fileLen = inputFile.length();
        int expectedSplits;
        // if file length and chunk size are exactly divisible
        if (fileLen % chunkSize == 0) {
            expectedSplits = (int) (fileLen / chunkSize);
        } else {
            expectedSplits = (int) ((fileLen / chunkSize) + 1);
        }

        long offset = 0;
        RandomAccessFile randomAccessFile = new RandomAccessFile(inputFile, "r");
        for (int i = 0; i < expectedSplits; i++) {
            long adjustedLength = chunkSize;
            try {
                // seek into the file and read the value. Read until we encounter a space of EOF
                randomAccessFile.seek(offset + adjustedLength);
                char charAtOffset = (char) randomAccessFile.readByte();
                while (charAtOffset != ' ') {
                    adjustedLength++;
                    try {
                        charAtOffset = (char) randomAccessFile.readByte();
                    } catch (EOFException eof) {
                        // ignore as we reached EOF
                        break;
                    }
                }
            } catch (EOFException eof) {
                // ignore as we reached EOF
            }
            LOG.info("Adding split from offset: {} length: {} for file: {}", offset, adjustedLength,
                    inputFile.getAbsolutePath());
            splits.add(new FileSplit(inputFile.getAbsolutePath(), offset, adjustedLength));
            offset += adjustedLength;
        }
        randomAccessFile.close();
        return splits;
    }

    @Override
    public JoinResponse join(String hostname, int port) throws TException {
        String hostInfo = hostname + ":" + port;
        liveNodes.add(hostInfo);
        LOG.info(hostInfo + " joined the cluster. Returning response: " + joinResponse);
        return joinResponse;
    }

    @Override
    public void heartbeat(String hostname, int port) throws TException {
        String hostInfo = hostname + ":" + port;
        if (heartbeatMap.containsKey(hostInfo)) {
            Stopwatch stopwatch = heartbeatMap.get(hostInfo);
            stopwatch.reset();
            stopwatch.start();
        } else {
            Stopwatch stopwatch = Stopwatch.createStarted();
            heartbeatMap.put(hostInfo, stopwatch);
            LOG.info("Received first heartbeat from " + hostInfo);
        }
    }

    private class SortResponseListener implements FutureCallback<SortResponse> {
        private FileSplit fileSplit;
        private CountDownLatch countDownLatch;
        private List<SortResponse> sortResponses;
        private List<FileSplit> failedSplits;
        private Set<FileSplit> sortJobs;
        private String host;
        private int port;

        public SortResponseListener(FileSplit fileSplit, CountDownLatch sortCountDownLatch,
                                    List<SortResponse> sortResponses, List<FileSplit> failedSplits,
                                    Set<FileSplit> sortJobs, String host, int port) {
            this.fileSplit = fileSplit;
            this.countDownLatch = sortCountDownLatch;
            this.sortResponses = sortResponses;
            this.failedSplits = failedSplits;
            this.sortJobs = sortJobs;
            this.host = host;
            this.port = port;
        }

        @Override
        public void onSuccess(SortResponse sortResponse) {
            if (sortResponse.getStatus().equals(Status.SUCCESS)) {
                if (sortJobs.contains(fileSplit)) {
                    sortJobs.remove(fileSplit);
                    countDownLatch.countDown();
                    sortResponses.add(sortResponse);
                    LOG.info("SUCCESS for " + fileSplit + " remaining: " + countDownLatch.getCount());
                } else {
                    TTransport socket = new TSocket(host, port);
                    try {
                        socket.open();
                        TProtocol protocol = new TBinaryProtocol(socket);
                        SlaveEndPoints.Client client = new SlaveEndPoints.Client(protocol);
                        Status status = client.killSort(fileSplit);
                        LOG.info("Kill sort for " + fileSplit + " received: " + status);
                    } catch (TTransportException e) {
                        e.printStackTrace();
                    } catch (TException e) {
                        e.printStackTrace();
                    } finally {
                        if (socket != null) {
                            socket.close();
                        }
                    }
                }
            } else {
                failedSplits.add(fileSplit);
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            failedSplits.add(fileSplit);
        }
    }
}
