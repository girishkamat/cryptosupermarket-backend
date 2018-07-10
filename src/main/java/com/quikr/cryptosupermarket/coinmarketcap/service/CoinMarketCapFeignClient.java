package com.quikr.cryptosupermarket.coinmarketcap.service;

import com.quikr.cryptosupermarket.domain.ListingsResult;
import com.quikr.cryptosupermarket.domain.ListingsWithPriceResults;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "coinmarketcap", url = "https://api.coinmarketcap.com")
public interface CoinMarketCapFeignClient {
    @RequestMapping(value = "/v2/listings", method = RequestMethod.GET)
    ListingsResult listings();

    @RequestMapping(value = "/v2/ticker/?structure=array", method = RequestMethod.GET)
    ListingsWithPriceResults listingsWithPrices(@RequestParam("start") int start, @RequestParam("limit") int limit, @RequestParam("convert") String currency);

}
