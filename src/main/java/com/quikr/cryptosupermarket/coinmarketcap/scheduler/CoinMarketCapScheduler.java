package com.quikr.cryptosupermarket.coinmarketcap.scheduler;

import com.quikr.cryptosupermarket.coinmarketcap.service.CoinMarketCapDataCache;
import com.quikr.cryptosupermarket.domain.CoinWithPrice;
import com.quikr.cryptosupermarket.domain.Currency;
import com.quikr.cryptosupermarket.domain.ListingsResult;
import com.quikr.cryptosupermarket.domain.ListingsWithPriceResults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Log4j2
public class CoinMarketCapScheduler {

    @Autowired
    private CoinMarketCapDataCache coinMarketCapDataCache;

    //@Scheduled(fixedRate = 5 * 60 * 1000)
    public void fetchData() {
        log.info("updating listings");
        ListingsResult listingsResult = coinMarketCapDataCache.fetchListings();
        coinMarketCapDataCache.listings(listingsResult);

        int numberOfCoins = listingsResult.getData().size();
        Arrays.stream(Currency.values()).forEach(currency -> {
            log.info("updating listings with prices [currency={}]", currency);
            int start = 1;
            int limit = 100;
            List<CoinWithPrice> coinWithPrices = new ArrayList<>();
            do {
                coinWithPrices.addAll(coinMarketCapDataCache
                        .fetchListingsWithPrices(start, limit, currency.name()).getData());
                start += limit;
                sleep();
            } while (start < numberOfCoins);
            coinMarketCapDataCache.listingsWithPrices(currency.name(),
                    ListingsWithPriceResults.builder().data(coinWithPrices).build());
        });
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
            //ignore
        }
    }
}
