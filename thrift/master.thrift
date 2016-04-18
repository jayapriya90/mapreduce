include "shared.thrift"

namespace java edu.umn.cs.mapreduce

struct JobRequest {
    1: required string inputFile;
    2: optional i64 chunkSize;
}

enum JobStatus {
    SUCCESS,
    NO_NODES_IN_CLUSTER
}

struct JobStats {
    1: required i32 numSplits;
    2: required i32 totalSortTasks;
    3: required i32 totalSuccessfulSortTasks;
    4: required i32 totalFailedSortTasks;
    5: required i32 totalMergeTasks;
    6: required i32 totalSuccessfulMergeTasks;
    7: required i32 totalFailedMergeTasks;
    8: required i64 averageTimeToSort;
    9: required i64 averageTimeToMerge;
}

struct JobResponse {
    1: required JobStatus status;
    2: optional string outputFile;
    3: optional JobStats jobStats;
    4: optional i64 executionTime;
}

struct JoinResponse {
    1: required double failProbability;
    2: required i32 heartbeatInterval;
}

service MasterEndPoints {
    // Used by clients
    JobResponse submit(1: JobRequest request);

    // Used by slaves
    JoinResponse join(1:string hostname, 2:i32 port);
    void heartbeat(1:string hostname, 2:i32 port);
}