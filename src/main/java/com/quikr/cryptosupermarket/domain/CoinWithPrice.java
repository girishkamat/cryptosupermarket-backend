package com.quikr.cryptosupermarket.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@ToString
public class CoinWithPrice {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("website_slug")
    private String websiteSlug;
    @JsonProperty("rank")
    private int rank;
    @JsonProperty("circulating_supply")
    private long circulatingSupply;
    @JsonProperty("total_supply")
    private long totalSupply;
    @JsonProperty("max_supply")
    private long maxSupply;
    @JsonProperty("quotes")
    private Map<String, Price> quotes = new HashMap<>();
    @JsonProperty("last_updated")
    private long lastUpdated;

    @Builder
    @JsonCreator
    public CoinWithPrice(@JsonProperty("id") int id,
                         @JsonProperty("name")String name,
                         @JsonProperty("symbol")String symbol,
                         @JsonProperty("website_slug")String websiteSlug,
                         @JsonProperty("rank") int rank,
                         @JsonProperty("circulating_supply") long circulatingSupply,
                         @JsonProperty("total_supply") long totalSupply,
                         @JsonProperty("max_supply") long maxSupply,
                         @JsonProperty("quotes") Map<String, Price> quotes,
                         @JsonProperty("last_updated") long lastUpdated) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.websiteSlug = websiteSlug;
        this.rank = rank;
        this.circulatingSupply  = circulatingSupply;
        this.totalSupply = totalSupply;
        this.maxSupply = maxSupply;
        this.quotes = quotes;
        this.lastUpdated = lastUpdated;
    }
}
