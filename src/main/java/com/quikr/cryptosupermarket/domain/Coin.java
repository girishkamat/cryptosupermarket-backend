package com.quikr.cryptosupermarket.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
public class Coin {
    private int id;
    private String name;
    private String symbol;
    private String websiteSlug;

    @Builder
    @JsonCreator
    public Coin(@JsonProperty("id") int id, @JsonProperty("name")String name, @JsonProperty("symbol")String symbol, @JsonProperty("website_slug")String websiteSlug) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.websiteSlug = websiteSlug;
    }
}
