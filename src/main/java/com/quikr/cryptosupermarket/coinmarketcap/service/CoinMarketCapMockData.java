package com.quikr.cryptosupermarket.coinmarketcap.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quikr.cryptosupermarket.domain.Currency;
import com.quikr.cryptosupermarket.domain.ListingsResult;
import com.quikr.cryptosupermarket.domain.ListingsWithPriceResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CoinMarketCapMockData {

    @Value("classpath:coinmarketcap-listings.json")
    private Resource listings;

    @Value("classpath:coinmarketcap-listings-USD.json")
    private Resource listingsUSD;

    @Value("classpath:coinmarketcap-listings-EUR.json")
    private Resource listingsEUR;

    @Value("classpath:coinmarketcap-listings-GBP.json")
    private Resource listingsGBP;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, ListingsWithPriceResults> cache = new HashMap<>();

    private ListingsResult listingsResult;

    @PostConstruct
    public void loadListings() throws IOException {

        if(listingsUSD != null)
        cache.put(Currency.USD.name(), objectMapper.readValue(listingsUSD.getInputStream(),ListingsWithPriceResults.class));

        if(listingsEUR != null)
        cache.put(Currency.EUR.name(), objectMapper.readValue(listingsEUR.getInputStream(),ListingsWithPriceResults.class));

        if(listingsGBP != null)
        cache.put(Currency.GBP.name(), objectMapper.readValue(listingsGBP.getInputStream(),ListingsWithPriceResults.class));

        if(listings != null)
        listingsResult = objectMapper.readValue(listings.getInputStream(), ListingsResult.class);
    }

    public ListingsWithPriceResults getListingsWithPrices(String currency) {
        return cache.get(currency);
    }

    public ListingsResult getListings() {
        return listingsResult;
    }
}
