package com.quikr.cryptosupermarket.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Price {
    private String price;
    private long volume24h;
    private long marketCap;
    private String percentChange1h;
    private String percentChange24h;
    private String percentChange7d;

    @Builder
    @JsonCreator
    public Price(@JsonProperty("price") String price,
                 @JsonProperty("volume_24h") long volume24h,
                 @JsonProperty("market_cap") long marketCap,
                 @JsonProperty("percent_change_1h") String percentChange1h,
                 @JsonProperty("percent_change_24h") String percentChange24h,
                 @JsonProperty("percent_change_7d") String percentChange7d) {
        this.price = price;
        this.volume24h = volume24h;
        this.marketCap = marketCap;
        this.percentChange1h = percentChange1h;
        this.percentChange24h = percentChange24h;
        this.percentChange7d = percentChange7d;
    }
}
