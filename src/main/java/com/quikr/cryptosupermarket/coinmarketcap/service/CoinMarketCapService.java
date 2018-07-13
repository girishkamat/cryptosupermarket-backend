package com.quikr.cryptosupermarket.coinmarketcap.service;

import com.quikr.cryptosupermarket.domain.CoinWithPrice;
import com.quikr.cryptosupermarket.domain.ListingsResult;
import com.quikr.cryptosupermarket.domain.ListingsWithPriceResults;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Double.valueOf;
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

    public ListingsWithPriceResults listingsWithPrices(String currency, String search, String sort, int start, int limit) {
        ListingsWithPriceResults listingsWithPriceResults = coinMarketCapDataCache.listingsWithPrices(currency);

        List<CoinWithPrice> data = new ArrayList<>(listingsWithPriceResults.getData());
        if (isNotBlank(search)) {
            data = data.stream().filter(c -> c.getName().toLowerCase().contains(search.toLowerCase())
                    || c.getSymbol().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (isNotBlank(sort)) {
            data = new ArrayList<>(data); // clone the list
            String sortField = sort.substring(0, sort.indexOf(" "));
            String sortOrder = sort.substring(sort.indexOf(" ") + 1, sort.length());
            switch (sortField) {
                case "price":
                    data.sort((c1, c2) -> {
                        int compare = ObjectUtils
                                .compare(c1.getQuotes().get(currency).getPrice() == null ? null : valueOf(c1.getQuotes().get(currency).getPrice()),
                                        c2.getQuotes().get(currency).getPrice() == null ? null : valueOf(c2.getQuotes().get(currency).getPrice()));
                        return "asc".equals(sortOrder) ? compare : compare * -1;
                    });
                    break;
                case "percentage1h":
                    data.sort((c1, c2) -> {
                        int compare = ObjectUtils
                                .compare(c1.getQuotes().get(currency).getPercentChange1h() == null ? null : valueOf(c1.getQuotes().get(currency).getPercentChange1h()),
                                        c2.getQuotes().get(currency).getPercentChange1h() == null ? null : valueOf(c2.getQuotes().get(currency).getPercentChange1h()));
                        return "asc".equals(sortOrder) ? compare : compare * -1;
                    });
                    break;
                case "percentage24h":
                    data.sort((c1, c2) -> {
                        int compare = ObjectUtils
                                .compare(c1.getQuotes().get(currency).getPercentChange24h() == null ? null : valueOf(c1.getQuotes().get(currency).getPercentChange24h()),
                                        c2.getQuotes().get(currency).getPercentChange24h() == null ? null : valueOf(c2.getQuotes().get(currency).getPercentChange24h()));
                        return "asc".equals(sortOrder) ? compare : compare * -1;
                    });
                    break;
                case "percentage7d":
                    data.sort((c1, c2) -> {
                        int compare = ObjectUtils
                                .compare(c1.getQuotes().get(currency).getPercentChange7d() == null ? null : valueOf(c1.getQuotes().get(currency).getPercentChange7d()),
                                        c2.getQuotes().get(currency).getPercentChange7d() == null ? null : valueOf(c2.getQuotes().get(currency).getPercentChange7d()));
                        return "asc".equals(sortOrder) ? compare : compare * -1;
                    });
                    break;
            }
        }
        int fromIndex = start - 1;
        if (fromIndex < 0 || fromIndex >= data.size()) {
            return ListingsWithPriceResults.builder().metaData(listingsWithPriceResults.getMetaData()).build();
        }
        int toIndex = fromIndex + limit;
        if (toIndex > data.size()) {
            toIndex = data.size();
        }
        data = data.subList(fromIndex, toIndex);

        return ListingsWithPriceResults.builder()
                .data(data)
                .metaData(listingsWithPriceResults.getMetaData())
                .build();
    }
}
