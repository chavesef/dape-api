package com.dape.api.adapter.consumer;

import com.dape.api.adapter.dto.UpdateBetEvent;
import com.dape.api.usecase.service.TicketService;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UpdateBetConsumer {

    private final TicketService ticketService;

    public UpdateBetConsumer(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @KafkaListener(
            topics = "${kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ConsumerRecord<String, GenericRecord> myRecord) {
        GenericRecord genericRecord = myRecord.value();
        UpdateBetEvent updateBetEvent = new UpdateBetEvent(
                (long) genericRecord.get("idt_bet"),
                (int) genericRecord.get("bet_status"));

        ticketService.updateTicketStatus(updateBetEvent.getIdtBet(), updateBetEvent.getBetStatus());

    }
}
