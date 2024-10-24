package com.dape.api.adapter.repository;

import com.dape.api.domain.entity.Bet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

    @Query(
            value = "SELECT * " +
                    "FROM BET b " +
                    "WHERE (:datCreated IS NULL OR TRUNC(b.DAT_CREATED) = TO_DATE(:datCreated, 'YYYY-MM-DD')) " +
                    "AND (:datUpdated IS NULL OR TRUNC(b.DAT_UPDATED) = TO_DATE(:datUpdated, 'YYYY-MM-DD')) " +
                    "AND (:codBetStatus IS NULL OR b.COD_BET_STATUS = :codBetStatus)",
            nativeQuery = true
    )
    Page<Bet> findAllWithFilters(Pageable pageable, Integer codBetStatus, String datCreated, String datUpdated);
}
