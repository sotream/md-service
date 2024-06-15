package com.sotream.service;

import com.sotream.dto.MarketDataDTO;
import com.sotream.entity.MarketData;
import com.sotream.repository.MarketDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketDataService {

    private final MarketDataRepository marketDataRepository;

    public MarketDataService(MarketDataRepository marketDataRepository) {
        this.marketDataRepository = marketDataRepository;
    }

    public List<MarketDataDTO> getAllMarketData() {
        return marketDataRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MarketDataDTO addMarketData(MarketDataDTO marketDataDTO) {
        MarketData marketData = new MarketData();
        marketData.setSymbol(marketDataDTO.symbol());
        marketData.setPrice(marketDataDTO.price());
        MarketData savedData = marketDataRepository.save(marketData);
        return convertToDTO(savedData);
    }

    private MarketDataDTO convertToDTO(MarketData marketData) {
        return new MarketDataDTO(
                marketData.getId(),
                marketData.getSymbol(),
                marketData.getPrice(),
                marketData.getTimestamp()
        );
    }
}
