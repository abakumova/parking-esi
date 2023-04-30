package edu.tartu.esi;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserRequestMessageSerializer implements Serializer<UserRequestMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public byte[] serialize(String topic, UserRequestMessage data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(data).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SerializationException("Error serializing UserRequestMessage", e);
        }
    }

    @Override
    public void close() {
        // No resources to release
    }

}
