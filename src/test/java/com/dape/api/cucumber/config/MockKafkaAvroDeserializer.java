package com.dape.api.cucumber.config;

import com.dape.api.adapter.dto.UpdateBetEvent;
import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.avro.AvroSchema;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

public class MockKafkaAvroDeserializer extends KafkaAvroDeserializer {

    public MockKafkaAvroDeserializer() {
        super.schemaRegistry = new MockSchemaRegistryClient() {
            @Override
            public synchronized ParsedSchema getSchemaById(int id) {
                if (id == 1)
                    return new AvroSchema(UpdateBetEvent.SCHEMA$);
                else
                    return new AvroSchema(UpdateBetEvent.SCHEMA$);
            }
        };
    }
}