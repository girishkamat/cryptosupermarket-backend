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
    private int id;
    private String name;
    private String symbol;
    private String websiteSlug;
    private int rank;
    private long circulatingSupply;
    private long totalSupply;
    private long maxSupply;
    private Map<String, Price> quotes = new HashMap<>();
    private long lastUpdated;

    @Builder
    @JsonCreator
    public CoinWithPrice(@JsonProperty("id") int id,
                         @JsonProperty("name")String name,
                         @JsonProperty("symbol")String symbol,
                         @JsonProperty("websiteSlug")String websiteSlug,
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
