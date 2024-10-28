#!/bin/bash
cd $KAFKA_DIR
./bin/zookeeper-server-stop.sh
./bin/kafka-server-stop.sh
cd - 

docker compose down