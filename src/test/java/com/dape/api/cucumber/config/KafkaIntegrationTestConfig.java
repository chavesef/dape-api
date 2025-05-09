package com.dape.api.cucumber.config;

import com.dape.api.adapter.dto.UpdateBetEvent;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@TestConfiguration
public class KafkaIntegrationTestConfig {

    private final KafkaProperties kafkaProperties;
    private final String schemaRegistryUrl;

    public KafkaIntegrationTestConfig(
            @Value("${spring.kafka.properties.schema.registry.url}") String schemaRegistryUrl,
            KafkaProperties kafkaProperties) {
        this.schemaRegistryUrl = schemaRegistryUrl;
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConsumerFactory<String, UpdateBetEvent> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Consumer<String, UpdateBetEvent> consumer() {
        return consumerFactory().createConsumer();
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        final Map<String, Object> props = kafkaProperties.buildConsumerProperties(null);

        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        props.put("auto.offset.reset", "earliest");

        return props;
    }
}