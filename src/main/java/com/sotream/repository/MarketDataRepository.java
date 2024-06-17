package com.sotream.repository;

import com.sotream.entity.MarketData;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;

@Repository
public interface MarketDataRepository extends R2dbcRepository<MarketData, Long> {
    Flux<MarketData> findAllBySymbol(String symbol);
    Flux<MarketData> findBy(Pageable pageable);

    @Query("SELECT * FROM market_data WHERE t BETWEEN :startDate AND :endDate")
    Flux<MarketData> findByDateRange(OffsetDateTime startDate, OffsetDateTime endDate);
}
