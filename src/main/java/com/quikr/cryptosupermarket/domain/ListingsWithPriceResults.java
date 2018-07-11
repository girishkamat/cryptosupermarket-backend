package com.quikr.cryptosupermarket.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class ListingsWithPriceResults {
    @JsonProperty("data")
    private List<CoinWithPrice> data = new ArrayList<>();

    @JsonProperty("metadata")
    private MetaData metaData;

    @Builder
    @JsonCreator
    public ListingsWithPriceResults(@JsonProperty("data") List<CoinWithPrice> data,
                                    @JsonProperty("metadata") MetaData metaData) {
        this.data = data;
        this.metaData = metaData;
    }
}
