#!/bin/bash
cd $KAFKA_DIR
./bin/zookeeper-server-start.sh -daemon ./config/zookeeper.properties
./bin/kafka-server-start.sh -daemon ./config/server.properties
cd -

docker compose up -d
