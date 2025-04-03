package com.dape.api.adapter.producer;

import com.dape.api.adapter.dto.UpdateBetEvent;
import com.dape.api.adapter.dto.UpdateBetProducerEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UpdateBetProducer {
    private final KafkaTemplate<String, UpdateBetEvent> kafkaTemplate;

    private final String kafkaTopic;

    public UpdateBetProducer(KafkaTemplate<String, UpdateBetEvent> kafkaTemplate, @Value("${kafka.topic}") String kafkaTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopic = kafkaTopic;
    }

    public void produceUpdateBetEvent(UpdateBetProducerEvent updateBetProducerEvent) {
        final UpdateBetEvent updateBetEvent = createUpdateBetEvent(updateBetProducerEvent);
        kafkaTemplate.send(kafkaTopic, updateBetEvent);
    }

    private static UpdateBetEvent createUpdateBetEvent(UpdateBetProducerEvent updateBetProducerEvent) {
        return UpdateBetEvent.newBuilder()
                .setIdtBet(updateBetProducerEvent.getIdtBet())
                .setBetStatus(updateBetProducerEvent.getBetStatus())
                .build();
    }
}
