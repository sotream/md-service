package com.sotream.service;

import com.sotream.dto.MarketTradeDTO;
import com.sotream.entity.MarketData;
import com.sotream.repository.MarketDataRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class MarketDataService {

    private static final Logger logger = LogManager.getLogger(MarketDataService.class);
    private final MarketDataRepository marketDataRepository;

    public MarketDataService(MarketDataRepository marketDataRepository) {
        this.marketDataRepository = marketDataRepository;
    }

    public Flux<MarketTradeDTO> getAllMarketData(int page, int size) {
        return marketDataRepository.findBy(PageRequest.of(page, size))
                .map(this::convertToDTO);
    }

    public Flux<MarketTradeDTO> getMarketDataByDateRange(OffsetDateTime startDate, OffsetDateTime endDate) {
        return marketDataRepository.findByDateRange(startDate, endDate)
                .map(this::convertToDTO);
    }

    public void saveMarketData(MarketTradeDTO marketDataDTO) {
        MarketData marketData = new MarketData();
        marketData.setTradeConditions(marketDataDTO.c());
        marketData.setLastPrice(marketDataDTO.p());
        marketData.setSymbol(marketDataDTO.s());
        marketData.setMDDate(convertToOffsetDateTime(marketDataDTO.t()));
        marketData.setVolume(marketDataDTO.v());

        marketDataRepository.save(marketData)
                .doOnError(error -> logger.error("Error saving market data: {}", error.getMessage()))
                .subscribe();
    }

    private MarketTradeDTO convertToDTO(MarketData marketData) {
        return new MarketTradeDTO(
                marketData.getTradeConditions(),
                marketData.getLastPrice(),
                marketData.getSymbol(),
                convertToMillis(marketData.getMDDate()),
                marketData.getVolume()
        );
    }

    private OffsetDateTime convertToOffsetDateTime(Long millis) {
        if (millis == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
    }

    private Long convertToMillis(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toInstant().toEpochMilli();
    }
}
