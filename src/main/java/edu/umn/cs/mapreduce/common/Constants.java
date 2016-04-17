package edu.umn.cs.mapreduce.common;

/**
 * Created by jayapriya on 4/16/16.
 */
public class Constants {
    public static final String DEFAULT_HOST = "localhost";
    public static final int MASTER_SERVICE_PORT = 9090;
    public static final int DEFAULT_CHUNK_SIZE = 1 * 1024 * 1024; // 1MB
    public static final int DEFAULT_MERGE_BATCH_SIZE = 8;
    public static final int DEFAULT_TASK_REDUNDANCY = 2;
    public static final int HEARTBEAT_INTERVAL = 300;
    public static final double DEFAULT_NODE_FAIL_PROBABILITY = 0.1; // 10% probability for node failure
    public static final String DEFAULT_INTERMEDIATE_DIR = "./intermediate_dir";
}
