#!/usr/bin/env bash

echo compile...

# pass <topicName> <numOfRecsToProduce> as args

mvn -q clean compile exec:java -e \
 -Dexec.mainClass="com.demo.kafka.SimpleProducer" \
 -Dexec.args="producer $1"
