package edu.umn.cs.mapreduce.master;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import edu.umn.cs.mapreduce.*;
import edu.umn.cs.mapreduce.common.Constants;
import org.apache.thrift.TException;
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
    private ExecutorService taskThreadPool;

    public MasterEndPointsImpl(int chunkSize, int mergeFilesBatchSize, int heartbeatInterval, int taskRedundancy,
                               double failProbability) {
        this.liveNodes = new HashSet<String>();
        this.heartbeatMap = new HashMap<String, Stopwatch>();
        this.chunkSize = chunkSize;
        this.mergeBatchSize = mergeFilesBatchSize;
        this.heartbeatInterval = heartbeatInterval;
        this.taskRedundancy = taskRedundancy;
        this.joinResponse = new JoinResponse(failProbability, heartbeatInterval);
        this.taskThreadPool = Executors.newFixedThreadPool(100);
    }

    @Override
    public JobResponse submit(JobRequest request) throws TException {
        long start = System.currentTimeMillis();
        if (liveNodes.isEmpty()) {
            LOG.error("No nodes are alive in the cluster. Failing request: " + request);
            return new JobResponse(JobStatus.NO_NODES_IN_CLUSTER);
        }

        LOG.info("Processing request: " + request);
        List<FileSplit> fileSplits = null;
        long requestedChunkSize = chunkSize;
        if (request.isSetChunkSize()) {
            requestedChunkSize = request.getChunkSize();
        }
        try {
            fileSplits = computeSplits(request.getInputDir(), requestedChunkSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileSplits.isEmpty()) {
            return new JobResponse(JobStatus.NO_FILES_IN_INPUT_DIR);
        }

        LOG.info("Input Dir: {} Chunk Size: {} Num Splits: {}", request.getInputDir(),
                requestedChunkSize, fileSplits.size());

        List<SortResponse> sortResponses = scheduleSortJobs(fileSplits);
        MergeResponse finalMerge = batchAndScheduleMergeJobs(sortResponses, mergeBatchSize);
        LOG.info("Final merge response: " + finalMerge);

        moveFileToDestination(finalMerge.getIntermediateFilePath(), request.getOutputDir());

        deleteAllIntermediateFiles();

        JobResponse response = new JobResponse(JobStatus.SUCCESS);
        long end = System.currentTimeMillis();
        response.setExecutionTime(end - start);
        LOG.info("Job finished successfully in " + (end - start) + " ms. Sending response: " + response);
        return response;
    }

    private void deleteAllIntermediateFiles() {
        File intDir = new File(Constants.DEFAULT_INTERMEDIATE_DIR);
        for (File file : intDir.listFiles()) {
            file.delete();
        }
        LOG.info("Removed all intermediate files..");
    }

    private void moveFileToDestination(String intermediateFilePath, String outputDir) {
        File outDir = new File(outputDir);
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
    }

    private MergeResponse batchAndScheduleMergeJobs(List<SortResponse> sortResponses, int mergeBatchSize) {
        List<String> filesToMerge = getAllFilesToMerge(sortResponses);
        MergeResponse finalMergeResponse = batchMergeJobs(filesToMerge, mergeBatchSize);
        return finalMergeResponse;
    }

    private MergeResponse batchMergeJobs(List<String> filesToMerge, int mergeBatchSize) {
        List<List<String>> batches = Lists.partition(filesToMerge, mergeBatchSize);
        LOG.info("Total files to merge: {} batchSize: {} numBatches: {}", filesToMerge.size(), mergeBatchSize,
                batches.size());

        List<MergeResponse> mergeResponses = scheduleMergeJobs(batches);
        MergeResponse finalMergeResponse;
        if (mergeResponses.size() == 1) {
            finalMergeResponse = mergeResponses.get(0);
        } else {
            List<String> nextFilesToMerge = getRemainingFilesToMerge(mergeResponses);
            finalMergeResponse = batchMergeJobs(nextFilesToMerge, mergeBatchSize);
        }
        return finalMergeResponse;
    }

    private List<MergeResponse> scheduleMergeJobs(List<List<String>> mergeBatches) {
        // round robin assignment of merge jobs to all liveNodes
        Iterator<String> circularIterator = Iterables.cycle(liveNodes).iterator();
        List<Future<MergeResponse>> futureMergeResponses = new ArrayList<Future<MergeResponse>>();
        for (List<String> mergeBatch : mergeBatches) {
            String hostInfo = circularIterator.next();
            if (isAlive(hostInfo)) {
                LOG.info(hostInfo + " is alive. Scheduling merge batch: " + mergeBatch + " for MERGE");
                String[] tokens = hostInfo.split(":");
                Future<MergeResponse> future = taskThreadPool.submit(new MergeRequestThread(tokens[0],
                        Integer.parseInt(tokens[1]), mergeBatch));
                futureMergeResponses.add(future);
            } else {
                LOG.info(hostInfo + " is not alive. Removing from live node list.");
                circularIterator.remove();
            }
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
                } else {
                    // failed responses means the corresponding requests has to be rescheduled for execution
                    List<String> failedBatch = mergeBatches.get(index);
                    failedBatches.add(failedBatch);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            index++;
        }

        // if there are any failed responses then reschedule those requests again
        if (!failedBatches.isEmpty()) {
            LOG.info(failedBatches.size() + " merge requests FAILED. Rescheduling the failed requests..");
            List<MergeResponse> responsesForFailedTasks = scheduleMergeJobs(failedBatches);
            mergeResponses.addAll(responsesForFailedTasks);
        }
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

    private List<SortResponse> scheduleSortJobs(List<FileSplit> fileSplits) {
        // round robin assignment of sort jobs to all liveNodes
        Iterator<String> circularIterator = Iterables.cycle(liveNodes).iterator();
        List<Future<SortResponse>> futureSortResponses = new ArrayList<Future<SortResponse>>();
        for (FileSplit fileSplit : fileSplits) {
            String hostInfo = circularIterator.next();
            if (isAlive(hostInfo)) {
                LOG.info(hostInfo + " is alive. Scheduling file split: " + fileSplit + " for SORT");
                String[] tokens = hostInfo.split(":");
                Future<SortResponse> future = taskThreadPool.submit(new SortRequestThread(tokens[0],
                        Integer.parseInt(tokens[1]), fileSplit));
                futureSortResponses.add(future);
            } else {
                LOG.info(hostInfo + " is not alive. Removing from live node list.");
                circularIterator.remove();
            }
        }

        LOG.info("Submitted all sort requests. #requests: " + fileSplits.size());
        List<SortResponse> sortResponses = new ArrayList<SortResponse>();
        int index = 0;
        List<FileSplit> failedSplits = new ArrayList<FileSplit>();
        for (Future<SortResponse> future : futureSortResponses) {
            try {
                SortResponse sortResponse = future.get();
                if (sortResponse.getStatus().equals(Status.SUCCESS)) {
                    sortResponses.add(future.get());
                } else {
                    FileSplit failedSplit = fileSplits.get(index);
                    failedSplits.add(failedSplit);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            index++;
        }

        if (!failedSplits.isEmpty()) {
            LOG.info(failedSplits.size() + " sort requests FAILED. Rescheduling the failed requests..");
            List<SortResponse> responsesForFailedTasks = scheduleSortJobs(failedSplits);
            sortResponses.addAll(responsesForFailedTasks);
        }
        LOG.info("Got all sort responses back. #responses: " + sortResponses.size());
        return sortResponses;
    }

    private boolean isAlive(String hostInfo) {
        Stopwatch stopwatch = heartbeatMap.get(hostInfo);
        long elapsedTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        // if elapsed time of this node's stopwatch is greater than twice that of heartbeat interval, let's assume the
        // node to be dead and not schedule any tasks to it
        if (elapsedTime > 2 * heartbeatInterval) {
            LOG.info("Stopwatch elapsed time: {} ms for node: {} is greater than twice of heartbeat interval: {}",
                    elapsedTime, hostInfo, heartbeatInterval);
            LOG.info("Assuming host: {} to be DEAD", hostInfo);
            return false;
        }
        return true;
    }

    private List<FileSplit> computeSplits(String inputDir, long chunkSize) throws IOException {
        List<File> allFiles = new ArrayList<File>();
        getAllFiles(inputDir, allFiles);
        List<FileSplit> splits = new ArrayList<FileSplit>();
        LOG.info("Total files in directory " + inputDir + " is: " + allFiles.size());
        for (File inputFile : allFiles) {
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
        }
        return splits;
    }

    private void getAllFiles(String inputDir, List<File> allFiles) {
        File path = new File(inputDir);
        for (File subPath : path.listFiles()) {
            if (subPath.isDirectory()) {
                getAllFiles(subPath.getAbsolutePath(), allFiles);
            } else {
                allFiles.add(subPath);
            }
        }
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
}
