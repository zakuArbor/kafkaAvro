package com.demo.kafka;

import java.util.Properties;

public class KafkaProperties {
	private Properties props = null;
	public KafkaProperties() {
		props = new Properties();
	    props.put("bootstrap.servers", "localhost:9092");
	     
	    props.put("retries", 0);
	      
	    props.put("batch.size", 16384);
	    props.put("linger.ms", 1);
	    props.put("buffer.memory", 33554432);
	    props.put("key.serializer", 
	         "org.apache.kafka.common.serialization.StringSerializer");
	    props.put("value.serializer", 
	         "com.demo.kafka.CustomSerializer");
	    props.put("group.id", "test");
	    props.put("enable.auto.commit", "true");
	    props.put("auto.commit.interval.ms", "1000");
	    props.put("session.timeout.ms", "30000");
	    props.put("key.deserializer", 
	         "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("value.deserializer", 
	         "com.demo.kafka.CustomDeserializer");
	}
	public Properties getProperties() {
		return props;
	}
}
