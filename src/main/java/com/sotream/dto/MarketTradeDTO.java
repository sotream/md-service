package com.sotream.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MarketTradeDTO(
        @JsonProperty("c") Double c,
        @JsonProperty("p") Double p,
        @JsonProperty("s") String s,
        @JsonProperty("t") Long t,
        @JsonProperty("v") Double v
) {
}
