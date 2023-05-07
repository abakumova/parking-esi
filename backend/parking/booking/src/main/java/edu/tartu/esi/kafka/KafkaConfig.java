package edu.tartu.esi.kafka;

import edu.tartu.esi.dto.BookingDto;
import edu.tartu.esi.model.Booking;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, BookingDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, BookingDto> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, BookingDto.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }

//    @Bean
//    public ConsumerFactory<String, BookingDto> consumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "management-group");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        return new DefaultKafkaConsumerFactory<>(config);
//    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, BookingDto> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, BookingDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setBatchListener(true);
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
//        return factory;
//    }
}