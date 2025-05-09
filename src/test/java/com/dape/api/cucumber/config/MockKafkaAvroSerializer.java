package com.dape.api.cucumber.config;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class MockKafkaAvroSerializer extends KafkaAvroSerializer {

    public MockKafkaAvroSerializer() {
        super.schemaRegistry = new MockSchemaRegistryClient();
    }
}
