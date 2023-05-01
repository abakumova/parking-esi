package edu.tartu.esi.kafka;

import edu.tartu.esi.kafka.message.*;
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
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    @Bean
    public ProducerFactory<String, Object> producerFactory()  {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "bookingresponsemessage:" + BookingPriceResponseMessage.class.getName());
        config.put(JsonSerializer.TYPE_MAPPINGS, "bookingrequestmessage:" + BookingUpdateStatusMessage.class.getName());
        config.put(JsonSerializer.TYPE_MAPPINGS, "bookingrequestmessage:" + BookingPriceRequestMessage.class.getName());
        config.put(JsonSerializer.TYPE_MAPPINGS, "managementrequestmessage:" + SlotCreatedMessage.class.getName());
        config.put(JsonSerializer.TYPE_MAPPINGS, "managementrequestmessage:" + SlotDeletedMessage.class.getName());
        config.put(JsonSerializer.TYPE_MAPPINGS, "managementrequestmessage:" + SlotUpdatedMessage.class.getName());
        config.put(JsonSerializer.TYPE_MAPPINGS, "locationrequestmessage:" + LocationMessage.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, SlotDeletedMessage> kafkaTemplateSlotDeleted() {
        return new KafkaTemplate<>(producerFactorySlotDeleted());
    }
    @Bean
    public ProducerFactory<String, SlotDeletedMessage> producerFactorySlotDeleted() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "managementrequestmessage:" + SlotDeletedMessage.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public KafkaTemplate<String, BookingPriceResponseMessage> kafkaTemplateBookingPriceResponse() {
        return new KafkaTemplate<>(producerFactoryBookingPriceResponse());
    }
    @Bean
    public ProducerFactory<String, BookingPriceResponseMessage> producerFactoryBookingPriceResponse() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "bookingresponsemessage:" + BookingPriceResponseMessage.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public KafkaTemplate<String, BookingUpdateStatusMessage> kafkaTemplateBookingUpdateStatus() {
        return new KafkaTemplate<>(producerFactoryBookingUpdateStatus());
    }
    @Bean
    public ProducerFactory<String, BookingUpdateStatusMessage> producerFactoryBookingUpdateStatus() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "bookingrequestmessage:" + BookingUpdateStatusMessage.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public KafkaTemplate<String, BookingPriceRequestMessage> kafkaTemplateBookingPriceRequest() {
        return new KafkaTemplate<>(producerFactoryBookingPriceRequest());
    }
    @Bean
    public ProducerFactory<String, BookingPriceRequestMessage> producerFactoryBookingPriceRequest() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "bookingrequestmessage:" + BookingPriceRequestMessage.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public KafkaTemplate<String, SlotCreatedMessage> kafkaTemplateSlotCreated() {
        return new KafkaTemplate<>(producerFactorySlotCreated());
    }
    @Bean
    public ProducerFactory<String, SlotCreatedMessage> producerFactorySlotCreated() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "managementrequestmessage:" + SlotCreatedMessage.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public KafkaTemplate<String, SlotUpdatedMessage> kafkaTemplateSlotUpdated() {
        return new KafkaTemplate<>(producerFactorySlotUpdated());
    }
    @Bean
    public ProducerFactory<String, SlotUpdatedMessage> producerFactorySlotUpdated() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "managementrequestmessage:" + SlotUpdatedMessage.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public KafkaTemplate<String, LocationMessage> kafkaTemplateLocation() {
        return new KafkaTemplate<>(producerFactoryLocation());
    }
    @Bean
    public ProducerFactory<String, LocationMessage> producerFactoryLocation() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "locationrequestmessage:" + LocationMessage.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "availability-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}