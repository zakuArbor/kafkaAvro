package com.demo.kafka;

import java.util.Map;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.demo.kafka.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomDeserializer implements Deserializer<User> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public User deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Attempt to Deserialize");
            //return (User) objectMapper.readValue(new String(data, "UTF-8"), User.class); //breaks
            DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.getClassSchema());
            BinaryDecoder decoder = DecoderFactory.get().binaryDecoder (data, null);
            User u = userDatumReader.read (null, decoder);
            System.out.println(u);
            return u;
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to User");
        }
    }

    @Override
    public void close() {
    }
}
