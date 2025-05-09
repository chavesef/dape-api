package com.dape.api.cucumber.consumer;

import com.dape.api.adapter.dto.UpdateBetEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.stereotype.Component;

@Component
public class KafkaUpdateBetEventConsumer {
    private final Consumer<String, UpdateBetEvent> consumer;

    public KafkaUpdateBetEventConsumer(
            Consumer<String, UpdateBetEvent> consumer,
            @Value("${kafka.topic}") String updateBetTopicName) {
        this.consumer = consumer;
        consumer.subscribe(Collections.singletonList(updateBetTopicName));
    }

    public List<UpdateBetEvent> consumeUpdateBetEvents() {
        final List<UpdateBetEvent> events = new ArrayList<>();
        KafkaTestUtils.getRecords(consumer).forEach(kafkaRecord -> events.add(kafkaRecord.value()));
        return events;
    }
}
