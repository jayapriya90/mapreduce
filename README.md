# MapReduce
Distributed sort and merge implementation with fault tolerance (also supports
proactive fault tolerance)

How To Compile:
--------------
1) To compile this project simply run compile.sh script found in root directory.
2) After the build is successful, use the following instructions to run the program

Scripts for launching different components
------------------------------------------
Following 3 scripts are used for launching different services. Use the scripts in
same following order
1) master
2) slave
3) client

Options for master
------------------
master script should be run first before running any scripts. The usage for
master script is

usage: master
 -cs <arg>  Chunk size in bytes (default: 1048576)
 -hi <arg>  Heartbeat interval in milliseconds (default: 500)
 -bs <arg>  Batch size for merge operation (default: 8)
 -tr <arg>  Task redundancy for proactive fault tolerance (default: 1)
 -fp <arg>  Fail probability for a node (default: 0.0)
 -h         Help

To run with defaults,
> ./master
To run with different chunk size and different merge batch size
> ./master -cs 2000000 -bs 4
To run with different fail probability for nodes 
(Assumption here is all nodes have same probability of failure)
> ./master -fp 0.05
To enable proactive fault tolerance with 2 task for each sort and merge
> ./master -tr 2
To use different heartbeat interval and fail probability
NOTE: Keeping the interval short may result in nodes dying quickly before
starting the client and running the tests. So its recommended to increase
the hearbeat interval or decrease the fail probability to give some time
for the nodes to run.
> ./master -hi 3000 -fp 0.1

Options for slave
-----------------
slave script accepts one optional argument i.e, hostname of master. 
If no argument is specified, by default localhost will be used for master. 
This script can be run multiple times on a single host (will use different
ports for every run) or can be run on different machines. When master
and slave scripts are run on different machines it is mandatory to provide
the hostname for master

To run (master running on same host),
> ./slave
To run when master running on different host,
> ./slave <hostname-for-master>

Options for client
------------------
client script used to trigger the job that sorts and merges the input file.

Following is the usage guide for client script.

usage: client
 -i <arg>  Input file to be sorted
 -h <arg>  Hostname for master (default: localhost)
 --help    Help

To run the client, specify the required input file argument
> ./client -i <path-to-input-file>
To run the client with master running on different host
> ./client -i <path-to-input-file> -h <hostname-for-master>

Running the client will print sample output like below

> ./client -i input_dir/20000000
04:58:04.666 [main] INFO  edu.umn.cs.mapreduce.client.Client - Submitted request to master: JobRequest(inputFile:input_dir/20000000)
--------------------------------------------------------------------------
                                  JOB STATISTICS
--------------------------------------------------------------------------
Input file:                         input_dir/20000000
Ouput file:                         ./output_dir/output_sorted
Input file size:                    93.25 MB
Output file size:                   93.25 MB
Requested chunk size:               1 MB
Number of splits:                   94
Total sort tasks:                   94
Total successful sort tasks:        94
Total failed sort tasks:            0
Total killed sort tasks:            94
Total merge tasks:                  15
Total successful merge tasks:       15
Total failed merge tasks:           0
Total killed merge tasks:           14
Average time to sort:               138 ms
Average time to merge:              6634 ms
Overal execution time:              26263 ms
--------------------------------------------------------------------------