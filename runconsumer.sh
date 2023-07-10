#!/usr/bin/env bash

echo compile...

# pass <topicName> <numOfRecsToProduce> as args

mvn -q clean compile exec:java -e \
 -Dexec.mainClass="com.demo.kafka.SimpleConsumer" \
 -Dexec.args="consumer $1"
