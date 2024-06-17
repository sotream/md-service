package com.sotream.controller;

import com.sotream.dto.MarketTradeDTO;
import com.sotream.service.MarketDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/market-data")
public class MarketDataController {

    private static final Logger logger = LogManager.getLogger(MarketDataController.class);
    private final MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping
    public Flux<MarketTradeDTO> getAllMarketData(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDate
    ) {
        logger.debug("Call [GET] /api/market-data?page={}&size={}&startDate={}&endDate={}", page, size, startDate, endDate);

        if (startDate != null && endDate != null) {
            // Query by date range
            return marketDataService.getMarketDataByDateRange(startDate, endDate);
        } else {
            // Default: Get all with pagination
            return marketDataService.getAllMarketData(page, size);
        }
    }
}
