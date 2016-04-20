package edu.umn.cs.mapreduce.slave;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
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

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jayapriya on 3/1/16.
 */

public class SlaveEndPointsImpl implements SlaveEndPoints.Iface {
    private static final Logger LOG = LoggerFactory.getLogger(SlaveEndPointsImpl.class);
    private static final AtomicBoolean alive = new AtomicBoolean(true);
    private ExecutorService executorService;
    private static final AtomicLong fileId = new AtomicLong(0);
    private String filePrefix;
    private ExecutorService sortExecutorService;
    private Map<FileSplit, Future<SortResponse>> fileSplitStatusMap;

    public SlaveEndPointsImpl(String masterHost, int heartbeatInterval, double failProbability,
                              String slaveHost, int slavePort) throws TTransportException {
        this.executorService = Executors.newSingleThreadExecutor();
        this.executorService.execute(new HeartBeatThread(masterHost, heartbeatInterval, slaveHost, slavePort, failProbability));
        this.filePrefix = "/file_" + slaveHost + "_" + slavePort + "_";
        this.sortExecutorService = Executors.newSingleThreadExecutor();
        this.fileSplitStatusMap = new ConcurrentHashMap<FileSplit, Future<SortResponse>>();
    }

    // used for testing
    public SlaveEndPointsImpl(String slaveHost, String slavePort) {
        this.filePrefix = "/file_" + slaveHost + "_" + slavePort + "_";
    }

    public static class HeartBeatThread implements Runnable {
        private static final Logger LOG = LoggerFactory.getLogger(HeartBeatThread.class);
        private int heartbeatInterval;
        private MasterEndPoints.Client client;
        private TTransport socket;
        private String slaveHost;
        private int slavePort;
        private Random rand;
        private double failProbability;

        public HeartBeatThread(String masterHost, int heartbeatInterval,
                               String slaveHost, int slavePort, double failProbability) throws TTransportException {
            this.heartbeatInterval = heartbeatInterval;
            this.slaveHost = slaveHost;
            this.slavePort = slavePort;
            this.rand = new Random();
            this.failProbability = failProbability;
            socket = new TSocket(masterHost, Constants.MASTER_SERVICE_PORT);
            socket.open();

            // create protocol for the superNodeSocket
            TProtocol protocol = new TBinaryProtocol(socket);

            // create the client for master's service
            client = new MasterEndPoints.Client(protocol);
            LOG.info("Started heart beat thread with heartbeatInterval: {}", heartbeatInterval);
        }

        @Override
        public void run() {
            while (true && alive.get()) {
                // send heartbeat
                try {
                    injectFault();
                    if (socket.isOpen()) {
                        client.heartbeat(slaveHost, slavePort);
                    } else {
                        LOG.info("Master closed connection to slave. Failing heartbeat.");
                        break;
                    }
                    Thread.sleep(heartbeatInterval);
                } catch (Exception e) {
                    if (socket != null) {
                        socket.close();
                    }
                    e.printStackTrace();
                }
            }
        }

        private void injectFault() {
            // get a random double and see if its value is less than fail probability. Since random is uniformly
            // distributed we can assume that probability of occurrence of value less than fail probability as
            // node failure probability.
            double nextDouble = rand.nextDouble();
            // if random value is less than fail probability then we mark the node as dead and not send heartbeat
            if (nextDouble < failProbability) {
                LOG.info("Random event is less than fail probability. Marking slave with host: {} slavePort: {} as DEAD",
                        slaveHost, slavePort);
                // mark node as dead
                alive.set(false);
            }
        }
    }

    private class SortExecutor implements Callable<SortResponse> {
        private FileSplit fileSplit;

        public SortExecutor(FileSplit fileSplit) {
            this.fileSplit = fileSplit;
        }

        @Override
        public SortResponse call() throws Exception {
            long start = System.currentTimeMillis();
            SortResponse response = null;
            if (!alive.get()) {
                return new SortResponse(Status.NODE_FAILED);
            }

            File file = new File(fileSplit.getFilename());
            RandomAccessFile randomAccessFile = null;
            try {
                randomAccessFile = new RandomAccessFile(file, "r");
                // seek to the specific offset
                randomAccessFile.seek(fileSplit.getOffset());

                // read contents as byte array
                byte[] bytes = new byte[(int) fileSplit.getLength()];
                randomAccessFile.read(bytes);

                // convert to string
                String contents = new String(bytes);

                // split by white spaces
                String[] tokens = contents.split("\\s+");

                // convert to integer list
                List<Integer> input = new ArrayList<Integer>();
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        input.add(Integer.valueOf(token.trim()));
                    }
                }

                // sort the input list
                Collections.sort(input);

                // join the list by space delimiter
                String sortedString = Joiner.on(" ").join(input);

                // before writing checking once again to make sure node is alive
                if (!alive.get()) {
                    return new SortResponse(Status.NODE_FAILED);
                }

                // write to output intermediate file
                String outIntermediateFile = Constants.DEFAULT_INTERMEDIATE_DIR + filePrefix + fileId.incrementAndGet();
                File outFile = new File(outIntermediateFile);
                FileOutputStream fileOutputStream = new FileOutputStream(outFile);
                fileOutputStream.write(sortedString.getBytes());
                fileOutputStream.close();

                if (alive.get()) {
                    response = new SortResponse(Status.SUCCESS);
                    long end = System.currentTimeMillis();
                    response.setIntermediateFilePath(outIntermediateFile);
                    response.setExecutionTime(end - start);
                } else {
                    response = new SortResponse(Status.NODE_FAILED);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            LOG.info("Returning response: {} for file split: {}", response, fileSplit);
            return response;
        }
    }

    @Override
    public SortResponse sort(FileSplit fileSplit) throws TException {
        SortExecutor sortExecutor = new SortExecutor(fileSplit);
        Future<SortResponse> future = sortExecutorService.submit(sortExecutor);
        fileSplitStatusMap.put(fileSplit, future);
        SortResponse sortResponse = null;
        try {
            sortResponse = future.get();
        } catch (InterruptedException e) {
            sortResponse = new SortResponse(Status.KILLED);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            fileSplitStatusMap.remove(fileSplit);
        }
        return sortResponse;
    }

    @Override
    public Status killSort(FileSplit fileSplit) throws TException {
        if (fileSplitStatusMap.containsKey(fileSplit)) {
            Future<SortResponse> sortResponseFuture = fileSplitStatusMap.get(fileSplit);
            sortResponseFuture.cancel(true);
            fileSplitStatusMap.remove(fileSplit);
            return Status.KILLED;
        }
        return Status.ALREADY_DONE;
    }

    @Override
    public MergeResponse merge(List<String> intermediateFiles) throws TException {
        long start = System.currentTimeMillis();
        MergeResponse response;
        List<Integer> mergedIntegers = new ArrayList<Integer>();
        for (String intermediateFile : intermediateFiles) {
            File intFile = new File(intermediateFile);
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(intFile));
                // read contents as byte array
                byte[] bytes = new byte[(int) intFile.length()];
                bufferedInputStream.read(bytes);

                // convert to string
                String contents = new String(bytes);

                // split by white spaces
                String[] tokens = contents.split("\\s+");

                // convert to integer list
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        mergedIntegers.add(Integer.valueOf(token.trim()));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (alive.get()) {
            // sort the input list
            Collections.sort(mergedIntegers);

            // join the list by space delimiter
            String sortedString = Joiner.on(" ").join(mergedIntegers);

            // before writing checking once again to make sure node is alive
            if (!alive.get()) {
                return new MergeResponse(Status.NODE_FAILED);
            }

            // write to output intermediate file
            String mergedFileName = Constants.DEFAULT_INTERMEDIATE_DIR + filePrefix + fileId.incrementAndGet();
            try {
                File outFile = new File(mergedFileName);
                FileOutputStream fileOutputStream = new FileOutputStream(outFile);
                fileOutputStream.write(sortedString.getBytes());
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (alive.get()) {
                response = new MergeResponse(Status.SUCCESS);
                long end = System.currentTimeMillis();
                response.setIntermediateFilePath(mergedFileName);
                response.setExecutionTime(end - start);
            } else {
                response = new MergeResponse(Status.NODE_FAILED);
            }
        } else {
            response = new MergeResponse(Status.NODE_FAILED);
        }
        return response;
    }
}
