package edu.umn.cs.mapreduce.master;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import edu.umn.cs.mapreduce.*;
import edu.umn.cs.mapreduce.common.Constants;
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
    private double failProbability;
    private JoinResponse joinResponse;
    private ExecutorService taskThreadPool;

    public MasterEndPointsImpl(int chunkSize, int mergeFilesBatchSize, int heartbeatInterval, int taskRedundancy,
                               double fp) {
        this.liveNodes = new HashSet<String>();
        this.heartbeatMap = new HashMap<String, Stopwatch>();
        this.chunkSize = chunkSize;
        this.mergeBatchSize = mergeFilesBatchSize;
        this.heartbeatInterval = heartbeatInterval;
        this.taskRedundancy = taskRedundancy;
        this.failProbability = fp;
        this.joinResponse = new JoinResponse(fp, heartbeatInterval);
        this.taskThreadPool = Executors.newFixedThreadPool(100);
    }

    @Override
    public JobResponse submit(JobRequest request) throws TException {
        if (liveNodes.isEmpty()) {
            LOG.error("No nodes are alive in the cluster. Failing request: " + request);
            return new JobResponse(JobStatus.NO_NODES_IN_CLUSTER);
        }

        LOG.info("Processing request: " + request);
        List<FileSplit> fileSplits = null;
        long requestedChunkSize = Constants.DEFAULT_CHUNK_SIZE;
        if (request.isSetChunkSize()) {
            requestedChunkSize = request.getChunkSize();
        }
        try {
            fileSplits = computeSplits(request.getInputDir(), requestedChunkSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.info("Input Dir: {} Chunk Size: {} Num Splits: {}", request.getInputDir(),
                requestedChunkSize, fileSplits.size());

        scheduleSortJobs(fileSplits);
        return null;
    }

    private void scheduleSortJobs(List<FileSplit> fileSplits) {
        // round robin assignment of sort jobs to all liveNodes
        Iterator<String> circularIterator = Iterables.cycle(liveNodes).iterator();
        List<Future<SortResponse>> futureSortResponses = new ArrayList<Future<SortResponse>>();
        for(FileSplit fileSplit : fileSplits) {
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

        LOG.info("Submitted all sort requests");
        List<SortResponse> sortResponses = new ArrayList<SortResponse>();
        for(Future<SortResponse> future : futureSortResponses) {
            try {
                sortResponses.add(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        LOG.info("Got all sort responses back. " + sortResponses);
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
