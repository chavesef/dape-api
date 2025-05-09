package com.dape.api.adapter.repository;

import com.dape.api.domain.entity.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TicketRepository extends JpaRepository<Ticket, Long>, QuerydslPredicateExecutor<Ticket> {

    @Query(value = "select idt_ticket from ticket_bet where idt_bet = :idtBet", nativeQuery = true)
    List<Long> getTicketByBetId(Long idtBet);
}