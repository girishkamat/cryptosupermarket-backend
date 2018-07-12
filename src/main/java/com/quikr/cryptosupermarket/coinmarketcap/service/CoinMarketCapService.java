package com.quikr.cryptosupermarket.coinmarketcap.service;

import com.quikr.cryptosupermarket.domain.CoinWithPrice;
import com.quikr.cryptosupermarket.domain.ListingsResult;
import com.quikr.cryptosupermarket.domain.ListingsWithPriceResults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@Service
@CacheConfig(cacheNames = "coinmarketcap")
@Log4j2
public class CoinMarketCapService {

    @Autowired
    private CoinMarketCapDataCache coinMarketCapDataCache;

    public ListingsResult listings() {
        return coinMarketCapDataCache.listings();
    }

    public ListingsWithPriceResults listingsWithPrices(String currency, String search, int start, int limit) {
        ListingsWithPriceResults listingsWithPriceResults = coinMarketCapDataCache.listingsWithPrices(currency);

        List<CoinWithPrice> data = listingsWithPriceResults.getData();
        if(isNotBlank(search)) {
            data = data.stream().filter(c -> c.getName().toLowerCase().contains(search.toLowerCase())
                    || c.getSymbol().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }
        int fromIndex = start - 1;
        if(fromIndex < 0 || fromIndex >= data.size()) {
            return ListingsWithPriceResults.builder().metaData(listingsWithPriceResults.getMetaData()).build();
        }
        int toIndex = fromIndex + limit;
        if(toIndex > data.size()) {
            toIndex = data.size();
        }
        data = data.subList(fromIndex, toIndex);

        return ListingsWithPriceResults.builder()
                .data(data)
                .metaData(listingsWithPriceResults.getMetaData())
                .build();
    }
}
