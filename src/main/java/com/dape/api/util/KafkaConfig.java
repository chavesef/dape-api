package com.dape.api.util;

import com.dape.api.adapter.dto.UpdateBetEvent;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@EnableKafka
@Configuration
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;
    private final String schemaRegistryUrl;

    public KafkaConfig(KafkaProperties kafkaProperties, @Value("${spring.kafka.schema.registry.url}") String schemaRegistryUrl) {
        this.kafkaProperties = kafkaProperties;
        this.schemaRegistryUrl = schemaRegistryUrl;
    }

    @Bean
    public ProducerFactory<String, UpdateBetEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        final Map<String, Object> props = kafkaProperties.getProducer().buildProperties(null);
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put("bootstrap.servers", "localhost:9092");
        return props;
    }

    @Bean
    public KafkaTemplate<String, UpdateBetEvent> kafkaTemplate() { return new KafkaTemplate<>(producerFactory());}

    @Bean
    public ConsumerFactory<String, UpdateBetEvent> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        final Map<String, Object> props = kafkaProperties.getConsumer().buildProperties(null);
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put("bootstrap.servers", "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UpdateBetEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UpdateBetEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
