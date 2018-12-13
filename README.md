
# MongoDB Java Driver Application Example

This repository uses [MongoDB Java driver](https://mongodb.github.io/mongo-java-driver/) v3.6.4. 
See [MongoDB Java driver QuickStart](http://mongodb.github.io/mongo-java-driver/3.7/driver/getting-started/quick-start/) for more examples.

## Description

This application insert a single document in a continuous loop into a MongoDB deployment. The purpose is to demonstrate how to handle insert operations when there is a failover.

## Setup 

1. Spin up a replica set locally, or use MongoDB Atlas. 

2. Set an environment variable called `MONGODB_URI` to point the application to the deployment. For example:

```sh
export MONGODB_URI="mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=test&retryWrites=true"
```

3. Execute the application: 

```sh
mvn package
java -cp ./target/available-1.0-SNAPSHOT.jar com.available.Application
```

4. Stop one of the replica set members. 

## Example output 

```sh
At 1544702605183 inserted: 5c124a8d70d32484da3c0346
At 1544702605290 inserted: 5c124a8d70d32484da3c0347
At 1544702605396 inserted: 5c124a8d70d32484da3c0348
At 1544702605499 inserted: 5c124a8d70d32484da3c0349
MongoCommandException
com.mongodb.MongoCommandException: Command failed with error 11600: 'interrupted at shutdown' on server localhost:27019. The full response is { "operationTime" : { "$timestamp" : { "t" : 1544702605, "i" : 5 } }, "ok" : 0.0, "errmsg" : "interrupted at shutdown", "code" : 11600, "codeName" : "InterruptedAtShutdown", "$clusterTime" : { "clusterTime" : { "$timestamp" : { "t" : 1544702605, "i" : 5 } }, "signature" : { "hash" : { "$binary" : "AAAAAAAAAAAAAAAAAAAAAAAAAAA=", "$type" : "00" }, "keyId" : { "$numberLong" : "0" } } } }
Dec 13, 2018 11:03:25 PM com.mongodb.diagnostics.logging.JULLogger log
WARNING: Got socket exception on connection [connectionId{localValue:4, serverValue:23}] to localhost:27019. All connections to localhost:27019 will be closed.
Dec 13, 2018 11:03:25 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: Closed connection [connectionId{localValue:4, serverValue:23}] to localhost:27019 because there was a socket exception raised by this connection.
Dec 13, 2018 11:03:25 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: Exception in monitor thread while connecting to server localhost:27019
com.mongodb.MongoSocketOpenException: Exception opening socket
	at com.mongodb.connection.SocketStream.open(SocketStream.java:62)
	at com.mongodb.connection.InternalStreamConnection.open(InternalStreamConnection.java:126)
	at com.mongodb.connection.DefaultServerMonitor$ServerMonitorRunnable.run(DefaultServerMonitor.java:128)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.net.ConnectException: Connection refused (Connection refused)
	at java.net.PlainSocketImpl.socketConnect(Native Method)
	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
	at java.net.Socket.connect(Socket.java:589)
	at com.mongodb.connection.SocketStreamHelper.initialize(SocketStreamHelper.java:59)
	at com.mongodb.connection.SocketStream.open(SocketStream.java:57)
	... 3 more

Dec 13, 2018 11:03:25 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: No server chosen by WritableServerSelector from cluster description ClusterDescription{type=REPLICA_SET, connectionMode=MULTIPLE, serverDescriptions=[ServerDescription{address=localhost:27017, type=REPLICA_SET_SECONDARY, state=CONNECTED, ok=true, version=ServerVersion{versionList=[4, 0, 4]}, minWireVersion=0, maxWireVersion=7, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=7079733, setName='test', canonicalAddress=localhost:27017, hosts=[localhost:27018, localhost:27017, localhost:27019], passives=[], arbiters=[], primary='localhost:27019', tagSet=TagSet{[]}, electionId=null, setVersion=3, lastWriteDate=Thu Dec 13 23:03:14 AEDT 2018, lastUpdateTimeNanos=2699552964314204}, ServerDescription{address=localhost:27018, type=REPLICA_SET_SECONDARY, state=CONNECTED, ok=true, version=ServerVersion{versionList=[4, 0, 4]}, minWireVersion=0, maxWireVersion=7, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=3601090, setName='test', canonicalAddress=localhost:27018, hosts=[localhost:27018, localhost:27017, localhost:27019], passives=[], arbiters=[], primary='localhost:27019', tagSet=TagSet{[]}, electionId=null, setVersion=3, lastWriteDate=Thu Dec 13 23:03:14 AEDT 2018, lastUpdateTimeNanos=2699552960056398}, ServerDescription{address=localhost:27019, type=UNKNOWN, state=CONNECTING, exception={com.mongodb.MongoSocketOpenException: Exception opening socket}, caused by {java.net.ConnectException: Connection refused (Connection refused)}}]}. Waiting for 30000 ms before timing out
Dec 13, 2018 11:03:25 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: Monitor thread successfully connected to server with description ServerDescription{address=localhost:27017, type=REPLICA_SET_SECONDARY, state=CONNECTED, ok=true, version=ServerVersion{versionList=[4, 0, 4]}, minWireVersion=0, maxWireVersion=7, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=5816267, setName='test', canonicalAddress=localhost:27017, hosts=[localhost:27018, localhost:27017, localhost:27019], passives=[], arbiters=[], primary='null', tagSet=TagSet{[]}, electionId=null, setVersion=3, lastWriteDate=Thu Dec 13 23:03:25 AEDT 2018, lastUpdateTimeNanos=2699561215964397}
Dec 13, 2018 11:03:25 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: Monitor thread successfully connected to server with description ServerDescription{address=localhost:27018, type=REPLICA_SET_SECONDARY, state=CONNECTED, ok=true, version=ServerVersion{versionList=[4, 0, 4]}, minWireVersion=0, maxWireVersion=7, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=3052536, setName='test', canonicalAddress=localhost:27018, hosts=[localhost:27018, localhost:27017, localhost:27019], passives=[], arbiters=[], primary='null', tagSet=TagSet{[]}, electionId=null, setVersion=3, lastWriteDate=Thu Dec 13 23:03:25 AEDT 2018, lastUpdateTimeNanos=2699561217337834}
Dec 13, 2018 11:03:36 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: Monitor thread successfully connected to server with description ServerDescription{address=localhost:27017, type=REPLICA_SET_SECONDARY, state=CONNECTED, ok=true, version=ServerVersion{versionList=[4, 0, 4]}, minWireVersion=0, maxWireVersion=7, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=1429640, setName='test', canonicalAddress=localhost:27017, hosts=[localhost:27018, localhost:27017, localhost:27019], passives=[], arbiters=[], primary='localhost:27018', tagSet=TagSet{[]}, electionId=null, setVersion=3, lastWriteDate=Thu Dec 13 23:03:25 AEDT 2018, lastUpdateTimeNanos=2699571801871728}
Dec 13, 2018 11:03:36 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: Monitor thread successfully connected to server with description ServerDescription{address=localhost:27018, type=REPLICA_SET_SECONDARY, state=CONNECTED, ok=true, version=ServerVersion{versionList=[4, 0, 4]}, minWireVersion=0, maxWireVersion=7, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=1675354, setName='test', canonicalAddress=localhost:27018, hosts=[localhost:27018, localhost:27017, localhost:27019], passives=[], arbiters=[], primary='localhost:27018', tagSet=TagSet{[]}, electionId=7fffffff000000000000004e, setVersion=3, lastWriteDate=Thu Dec 13 23:03:25 AEDT 2018, lastUpdateTimeNanos=2699571801891959}
Dec 13, 2018 11:03:37 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: Monitor thread successfully connected to server with description ServerDescription{address=localhost:27018, type=REPLICA_SET_PRIMARY, state=CONNECTED, ok=true, version=ServerVersion{versionList=[4, 0, 4]}, minWireVersion=0, maxWireVersion=7, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=1481782, setName='test', canonicalAddress=localhost:27018, hosts=[localhost:27018, localhost:27017, localhost:27019], passives=[], arbiters=[], primary='localhost:27018', tagSet=TagSet{[]}, electionId=7fffffff000000000000004e, setVersion=3, lastWriteDate=Thu Dec 13 23:03:37 AEDT 2018, lastUpdateTimeNanos=2699573310980118}
Dec 13, 2018 11:03:37 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: Setting max election id to 7fffffff000000000000004e from replica set primary localhost:27018
Dec 13, 2018 11:03:37 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: Discovered replica set primary localhost:27018
Dec 13, 2018 11:03:37 PM com.mongodb.diagnostics.logging.JULLogger log
INFO: Opened connection [connectionId{localValue:30, serverValue:10}] to localhost:27018
At 1544702617838 inserted: 5c124a8d70d32484da3c034b
At 1544702617942 inserted: 5c124a9970d32484da3c034c
At 1544702618045 inserted: 5c124a9a70d32484da3c034d
At 1544702618149 inserted: 5c124a9a70d32484da3c034e
```


### Changing the MongoDB Java driver version

You can change the version of `mongo-java-driver` before compilation time by modifying the pom.xml: `mongo-java-driver` version.

