package edu.umn.cs.mapreduce.master;

/**
 * Created by jayapriya on 3/1/16.
 */

import edu.umn.cs.mapreduce.MasterEndPoints;
import edu.umn.cs.mapreduce.common.Constants;
import org.apache.commons.cli.*;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * usage: master
 * -cs <arg>  Chunk size in bytes (default: 1MB)
 * -hi <arg>  Heartbeat interval in milliseconds (default: 300)
 * -bs <arg>  Batch size for merge operation (default: 8)
 * -tr <arg>  Task redundancy for proactive fault tolerance (default: 2)
 * -fp <arg>  Fail probability for a node (default: 0.1)
 * -h         Help
 */

public class Master {
    private static final Logger LOG = LoggerFactory.getLogger(Master.class);
    private MasterEndPointsImpl masterEndPoints;
    private MasterEndPoints.Processor processor;

    public Master(int chunkSize, int heartbeatInterval, int batchSize, int taskRedundancy, double fp) {
        this.masterEndPoints = new MasterEndPointsImpl(chunkSize, batchSize, heartbeatInterval, taskRedundancy, fp);
        this.processor = new MasterEndPoints.Processor(masterEndPoints);
    }

    public void startService() {
        try {
            TServerTransport serverTransport = new TServerSocket(Constants.MASTER_SERVICE_PORT);
            // Use this for a multi-threaded server
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(
                    processor));

            LOG.info("Started the master endpoints at port {}", Constants.MASTER_SERVICE_PORT);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelpFormatter formatter = new HelpFormatter();

        // arguments that can be passed to this application
        Options options = new Options();
        options.addOption("cs", true, "Chunk size in bytes (default: 1MB)");
        options.addOption("hi", true, "Heartbeat interval in milliseconds (default: 300)");
        options.addOption("bs", true, "Batch size for merge operation (default: 8)");
        options.addOption("tr", true, "Task redundancy for proactive fault tolerance (default: 2)");
        options.addOption("fp", true, "Fail probability for a node (default: 0.1)");
        options.addOption("h", false, "Help");

        // command line parser for the above options
        CommandLineParser cliParser = new GnuParser();
        try {
            CommandLine cli = cliParser.parse(options, args);

            // print help
            if (cli.hasOption("h")) {
                formatter.printHelp("master", options);
                return;
            }

            int chunkSize = Constants.DEFAULT_CHUNK_SIZE;
            if (cli.hasOption("cs")) {
                chunkSize = Integer.parseInt(cli.getOptionValue("cs"));
            }

            int hearbeatInterval = Constants.HEARTBEAT_INTERVAL;
            if (cli.hasOption("hi")) {
                hearbeatInterval = Integer.parseInt(cli.getOptionValue("hi"));
            }

            int mergeBatchSize = Constants.DEFAULT_MERGE_BATCH_SIZE;
            if (cli.hasOption("bs")) {
                mergeBatchSize = Integer.parseInt(cli.getOptionValue("bs"));
            }

            int taskRedundancy = Constants.DEFAULT_TASK_REDUNDANCY;
            if (cli.hasOption("tr")) {
                taskRedundancy = Integer.parseInt(cli.getOptionValue("tr"));
            }

            double fp = Constants.DEFAULT_NODE_FAIL_PROBABILITY;
            if (cli.hasOption("fp")) {
                fp = Double.parseDouble(cli.getOptionValue("fp"));
            }

            final Master master = new Master(chunkSize, hearbeatInterval, mergeBatchSize, taskRedundancy, fp);
            // start the services offered by master in separate threads
            Runnable service = new Runnable() {
                public void run() {
                    master.startService();
                }
            };

            new Thread(service).start();
        } catch (ParseException e) {

            // if wrong format is specified
            System.err.println("Invalid option.");
            formatter.printHelp("master", options);
        }
    }
}
