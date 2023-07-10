package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleConsumer {
	private ObjectMapper objectMapper = new ObjectMapper();
    private final static int TIME_OUT_MS = 5000;
    private KafkaConsumer<String, String> kafkaConsumer = null;
    private final AtomicBoolean closed = new AtomicBoolean(false);

    public SimpleConsumer() throws Exception {
    }
    
    public static void main(String[] args) throws Exception {
    	//KafkaProperties kafkaProperties = new KafkaProperties();
    	Properties props = new Properties();
    	props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer_id");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.demo.kafka.CustomDeserializer");
        KafkaConsumer<String, String> kafkaConsumer =new KafkaConsumer<>(props);//(kafkaProperties.getProperties());
        try {
            kafkaConsumer.subscribe(List.of("sample-avro"));
            while (true) {
                ConsumerRecords<String, String> records =
                        kafkaConsumer.poll(Duration.ofMillis(TIME_OUT_MS));
               
                for (ConsumerRecord<String, String> record : records) {
                	System.out.println(record);
                }
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            kafkaConsumer.close();
        }
    }
}
