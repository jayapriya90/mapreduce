include "shared.thrift"

namespace java edu.umn.cs.mapreduce

struct JobRequest {
    1: required string inputDir;
    2: required string outputDir;
    3: optional i64 chunkSize;
}

enum JobStatus {
    SUCCESS,
    NO_NODES_IN_CLUSTER
}

struct JobStats {
    1: required i32 numSplits;
    2: required i32 totalSortJobs;
    3: required i32 totalSuccessful;
    4: required i32 totalKilled;
    5: required i32 totalFailed;
}

struct JobResponse {
    1: required JobStatus status;
    2: optional JobStats jobStats;
    3: optional i64 executionTime;
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