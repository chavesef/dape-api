package com.dape.api.adapter.repository;

import com.dape.api.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TicketRepository extends JpaRepository<Ticket, Long>, QuerydslPredicateExecutor<Ticket> {
}
