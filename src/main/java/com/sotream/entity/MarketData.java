package com.sotream.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "market_data")
public class MarketData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double c;
    private Double p;
    private String s;
    private OffsetDateTime t;
    private Double v;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTradeConditions() {
        return c;
    }

    public void setTradeConditions(Double c) {
        this.c = c;
    }

    public Double getLastPrice() {
        return p;
    }

    public void setLastPrice(Double p) {
        this.p = p;
    }

    public String getSymbol() {
        return s;
    }

    public void setSymbol(String s) {
        this.s = s;
    }

    public OffsetDateTime getMDDate() {
        return t;
    }

    public void setMDDate(OffsetDateTime t) {
        this.t = t;
    }

    public Double getVolume() {
        return v;
    }

    public void setVolume(Double v) {
        this.v = v;
    }
}
