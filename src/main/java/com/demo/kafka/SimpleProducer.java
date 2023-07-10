package com.demo.kafka;

import java.util.UUID;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleProducer {
	private final ObjectMapper objectMapper = new ObjectMapper();
    public SimpleProducer() throws Exception {
    }
    
    public byte[] serializer(User user) throws IOException {
    	/*Schema schema = new Schema.Parser().parse (new File("/home/zaku/Downloads/kafka-java-demo/src/main/resources/avro.avsc"));
    	DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(schema);
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	BinaryEncoder encoder = null;
    	encoder = EncoderFactory.get().binaryEncoder (out, encoder);
    	userDatumWriter.write(user, encoder);
    	encoder.flush();
    	byte[] bytes = out.toByteArray();
    	out.close();
    	//DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);*/
    	//try this below if all else fails
    	return objectMapper.writeValueAsBytes(user);
    	//return bytes;
    }

    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setName("Pikachu");
        user.setFavoriteColor("Yellow");
        user.setFavoriteNumber(42);

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "consumer_id");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.demo.kafka.CustomSerializer");

        ProducerRecord<String, User> producerRecord =
                new ProducerRecord<>("sample-avro", "key", user);
        KafkaProperties kafkaProperties = new KafkaProperties();
        
        KafkaProducer<String, User> kafkaProducer = new KafkaProducer<>(kafkaProperties.getProperties());
        kafkaProducer.send(producerRecord);
        kafkaProducer.close();
    }
}
