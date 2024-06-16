package com.sotream.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.sotream.dto.MarketDataDTO;
import com.sotream.service.MarketDataService;

@RestController
@RequestMapping("/api/market-data")
public class MarketDataController {

    private static final Logger logger = LogManager.getLogger(MarketDataController.class);
    private final MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping
    public ResponseEntity<List<MarketDataDTO>> getAllMarketData() {
        logger.debug("Call [GET] /api/market-data");

        return ResponseEntity.ok(marketDataService.getAllMarketData());
    }

    @PostMapping
    public ResponseEntity<MarketDataDTO> addMarketData(@RequestBody MarketDataDTO marketDataDTO) {
        return ResponseEntity.ok(marketDataService.addMarketData(marketDataDTO));
    }
}
