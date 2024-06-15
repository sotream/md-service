package com.sotream.controller;

import com.sotream.dto.MarketDataDTO;
import com.sotream.service.MarketDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market-data")
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping
    public ResponseEntity<List<MarketDataDTO>> getAllMarketData() {
        return ResponseEntity.ok(marketDataService.getAllMarketData());
    }

    @PostMapping
    public ResponseEntity<MarketDataDTO> addMarketData(@RequestBody MarketDataDTO marketDataDTO) {
        return ResponseEntity.ok(marketDataService.addMarketData(marketDataDTO));
    }
}
