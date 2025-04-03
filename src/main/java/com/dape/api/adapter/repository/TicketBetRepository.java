package com.dape.api.adapter.repository;

import com.dape.api.domain.entity.TicketBet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketBetRepository extends JpaRepository<TicketBet, Long> {
    @Query(value = "select idt_bet from ticket_bet where idt_ticket = :ticketId", nativeQuery = true)
    List<Long> findBetsByTicketId(Long ticketId);
}