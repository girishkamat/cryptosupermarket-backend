package com.quikr.cryptosupermarket.coinmarketcap.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quikr.cryptosupermarket.domain.ListingsResult;
import com.quikr.cryptosupermarket.domain.ListingsWithPriceResults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.FileWriter;

@Service
@CacheConfig(cacheNames = "coinmarketcap")
@Log4j2
public class CoinMarketCapDataCache {

    @Autowired
    private CoinMarketCapFeignClient coinMarketCapFeignClient;

    @Autowired
    private CoinMarketCapMockData coinMarketCapMockData;

    @Autowired
    private ObjectMapper objectMapper;

    @CachePut(cacheNames = "coinmarketcap", key = "{#root.methodName}")
    public ListingsResult listings(ListingsResult listingsResult) {
        return listingsResult;
    }

    @Cacheable(cacheNames = "coinmarketcap", key = "{#root.methodName}")
    public ListingsResult listings() {
        return coinMarketCapMockData.getListings();
    }

    public ListingsResult fetchListings() {
        return coinMarketCapFeignClient.listings();
    }

    @CachePut(cacheNames = "coinmarketcap", key = "{#root.methodName, #currency}")
    public ListingsWithPriceResults listingsWithPrices(String currency, ListingsWithPriceResults listingsWithPriceResults) {
        log.info("currency={}, numberOfCoins={}", currency, listingsWithPriceResults.getData().size());
        //writeToFile(currency, listingsWithPriceResults);
        return listingsWithPriceResults;
    }

    @Cacheable(cacheNames = "coinmarketcap", key = "{#root.methodName, #currency}")
    public ListingsWithPriceResults listingsWithPrices(String currency) {
        return coinMarketCapMockData.getListingsWithPrices(currency);
    }

    public ListingsWithPriceResults fetchListingsWithPrices(int start, int limit, String currency) {
        return coinMarketCapFeignClient.listingsWithPrices(start, limit, currency);
    }

    private void writeToFile(String currency, ListingsWithPriceResults listingsWithPriceResults) {
        try(FileWriter fileWriter = new FileWriter("src/main/resources/coinmarketcap-listings-"+ currency + ".json")) {
            objectMapper.writeValue(fileWriter, listingsWithPriceResults);
        } catch (Exception e) {
            log.error("failed to serialize as JSON", e);
        }
    }
}
