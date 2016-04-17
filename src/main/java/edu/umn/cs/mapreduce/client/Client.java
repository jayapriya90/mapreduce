package edu.umn.cs.mapreduce.client;

/**
 * Created by jayapriya on 3/1/16.
 */

import edu.umn.cs.mapreduce.JobRequest;
import edu.umn.cs.mapreduce.JobResponse;
import edu.umn.cs.mapreduce.MasterEndPoints;
import edu.umn.cs.mapreduce.common.Constants;
import org.apache.commons.cli.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * usage: client
 * -i <arg>  Input directory
 * -o <arg>  Output directory
 * -h <arg>  Hostname for master (default: localhost)
 * --help    Help
 */

public class Client {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws TException {
        HelpFormatter formatter = new HelpFormatter();

        // arguments that can be passed to this application
        Options options = new Options();
        options.addOption("i", true, "Input directory");
        options.addOption("o", true, "Output directory");
        options.addOption("h", true, "Hostname for master (default:localhost)");
        options.addOption("help", false, "Help");

        // command line parser for the above options
        CommandLineParser cliParser = new GnuParser();
        try {
            CommandLine cli = cliParser.parse(options, args);

            // print help
            if (cli.hasOption("help")) {
                formatter.printHelp("client", options);
                return;
            }

            // if hostname of master is specified use it
            String hostname = Constants.DEFAULT_HOST;
            if (cli.hasOption("h")) {
                hostname = cli.getOptionValue("h");
            }

            if (!cli.hasOption("i")) {
                System.err.println("Input directory must be specified");
                formatter.printHelp("client", options);
                return;
            }

            if (!cli.hasOption("o")) {
                System.err.println("Output directory must be specified");
                formatter.printHelp("client", options);
                return;
            }

            String inputDir = cli.getOptionValue("i");
            String outputDir = cli.getOptionValue("o");
            processCommand(hostname, inputDir, outputDir);
        } catch (ParseException e) {

            // if wrong format is specified
            System.err.println("Invalid option.");
            formatter.printHelp("client", options);
        }
    }

    private static void processCommand(String hostname, String inputDir, String outputDir) throws TException {
        TTransport nodeSocket = new TSocket(hostname, Constants.MASTER_SERVICE_PORT);
        try {
            nodeSocket.open();
            TProtocol protocol = new TBinaryProtocol(nodeSocket);
            MasterEndPoints.Client client = new MasterEndPoints.Client(protocol);
            JobRequest request = new JobRequest(inputDir, outputDir);
            LOG.info("Submitted request to master: " + request);
            JobResponse response = client.submit(request);
            System.out.println("Received response from master: " + response);
        } finally {
            if (nodeSocket != null) {
                nodeSocket.close();
            }
        }
    }
}
