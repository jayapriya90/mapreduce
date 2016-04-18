include "shared.thrift"

namespace java edu.umn.cs.mapreduce

enum Status {
    SUCCESS,
    NODE_FAILED
}

struct SortResponse {
    1: required Status status;
    2: optional string intermediateFilePath;
    3: optional i64 executionTime;
}

struct MergeResponse {
    1: required Status status;
    2: optional string intermediateFilePath;
    3: optional i64 executionTime;
}

service SlaveEndPoints {
    SortResponse sort(1: required shared.FileSplit fileSplit);
    MergeResponse merge(1: required list<string> intermediateFiles);
}