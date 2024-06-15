package com.sotream.dto;

import java.time.LocalDateTime;

public record MarketDataDTO(Long id, String symbol, Double price, LocalDateTime timestamp) {}
