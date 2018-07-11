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
public class MetaData {
    @JsonProperty("timestamp")
    private long timestamp;
    @JsonProperty("num_cryptocurrencies")
    private int numCryptoCurrencies;

    @Builder
    @JsonCreator
    public MetaData(@JsonProperty("timestamp") long timestamp,
                    @JsonProperty("num_cryptocurrencies") int numCryptoCurrencies) {
        this.timestamp = timestamp;
        this.numCryptoCurrencies = numCryptoCurrencies;
    }
}
