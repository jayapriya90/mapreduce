package edu.umn.cs.mapreduce.master;

import edu.umn.cs.mapreduce.FileSplit;
import edu.umn.cs.mapreduce.SlaveEndPoints;
import edu.umn.cs.mapreduce.SortResponse;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.concurrent.Callable;

/**
 * Created by jayapriya on 3/1/16.
 */
public class SortRequestThread implements Callable<SortResponse> {
    private String slaveHost;
    private int slavePort;
    private FileSplit fileSplit;

    public SortRequestThread(String slaveHost, int slavePort, FileSplit fileSplit) {
        this.slaveHost = slaveHost;
        this.slavePort = slavePort;
        this.fileSplit = fileSplit;
    }

    @Override
    public SortResponse call() throws Exception {
        TTransport socket = new TSocket(slaveHost, slavePort);
        socket.open();
        TProtocol protocol = new TBinaryProtocol(socket);
        SlaveEndPoints.Client client = new SlaveEndPoints.Client(protocol);
        SortResponse sortResponse = client.sort(fileSplit);
        return sortResponse;
    }
}
