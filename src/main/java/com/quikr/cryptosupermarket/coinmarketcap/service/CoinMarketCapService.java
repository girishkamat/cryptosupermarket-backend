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

    public ListingsWithPriceResults listingsWithPrices(String currency, String filter, int start, int limit) {
        ListingsWithPriceResults listingsWithPriceResults = coinMarketCapDataCache.listingsWithPrices(currency);
        int fromIndex = start - 1;
        if(fromIndex < 0 || fromIndex >= listingsWithPriceResults.getData().size()) {
            throw new IllegalArgumentException("start is lower than 1 or higher/equal to available size");
        }
        int toIndex = fromIndex + limit;
        if(toIndex > listingsWithPriceResults.getData().size()) {
            toIndex = listingsWithPriceResults.getData().size();
        }

        List<CoinWithPrice> data = listingsWithPriceResults.getData().subList(fromIndex, toIndex);
        if(isNotBlank(filter)) {
            data = data.stream().filter(c -> c.getSymbol().equalsIgnoreCase(filter)).collect(Collectors.toList());
        }

        return ListingsWithPriceResults.builder()
                .data(data)
                .metaData(listingsWithPriceResults.getMetaData())
                .build();
    }
}
