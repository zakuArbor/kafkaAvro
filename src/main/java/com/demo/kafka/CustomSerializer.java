package com.demo.kafka;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import com.demo.kafka.User;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomSerializer implements Serializer<User> {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, User data) {
        try {
            if (data == null){
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing Attempt");
            //return objectMapper.writeValueAsBytes(data); //breaks
            //Schema schema = new Schema.Parser().parse (new File("/home/zaku/Downloads/kafka-java-demo/src/main/resources/avro.avsc"));
        	DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.getClassSchema());
        			
        	ByteArrayOutputStream out = new ByteArrayOutputStream();
        	BinaryEncoder encoder = null;
        	encoder = EncoderFactory.get().binaryEncoder (out, encoder);
        	userDatumWriter.write(data, encoder);
        	encoder.flush();
        	byte[] bytes = out.toByteArray();
        	out.close();
        	return bytes;
        	//DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);*/
        } catch (Exception e) {
            throw new SerializationException("Error when serializing User to byte[]");
        }
    }

    @Override
    public void close() {
    }
}
