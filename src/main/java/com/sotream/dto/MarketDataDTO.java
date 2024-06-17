package com.sotream.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MarketDataDTO(
        @JsonProperty("data") List<MarketTradeDTO> data,
        @JsonProperty("type") String type
) {
}
