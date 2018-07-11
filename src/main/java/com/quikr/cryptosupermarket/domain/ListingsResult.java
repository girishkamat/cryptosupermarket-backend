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
public class ListingsResult {
    @JsonProperty("data")
    private List<Coin> data = new ArrayList<>();

    @JsonProperty("metadata")
    private MetaData metaData;

    @Builder
    @JsonCreator
    public ListingsResult(@JsonProperty("data") List<Coin> data,
                          @JsonProperty("metadata") MetaData metaData) {
        this.data = data;
        this.metaData = metaData;
    }
}
