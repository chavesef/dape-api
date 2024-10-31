package com.dape.api.adapter.repository;

import com.dape.api.domain.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long>, QuerydslPredicateExecutor<Bet> {
}
