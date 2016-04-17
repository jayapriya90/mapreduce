package edu.umn.cs.mapreduce.master;

import edu.umn.cs.mapreduce.FileSplit;
import edu.umn.cs.mapreduce.MergeResponse;
import edu.umn.cs.mapreduce.SlaveEndPoints;
import edu.umn.cs.mapreduce.SortResponse;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by jayapriya on 3/1/16.
 */
public class MergeRequestThread implements Callable<MergeResponse> {
    private String slaveHost;
    private int slavePort;
    private List<String> filesToMerge;

    public MergeRequestThread(String slaveHost, int slavePort, List<String> filesToMerge) {
        this.slaveHost = slaveHost;
        this.slavePort = slavePort;
        this.filesToMerge = filesToMerge;
    }

    @Override
    public MergeResponse call() throws Exception {
        TTransport socket = new TSocket(slaveHost, slavePort);
        socket.open();
        TProtocol protocol = new TBinaryProtocol(socket);
        SlaveEndPoints.Client client = new SlaveEndPoints.Client(protocol);
        MergeResponse mergeResponse = client.merge(filesToMerge);
        return mergeResponse;
    }
}
