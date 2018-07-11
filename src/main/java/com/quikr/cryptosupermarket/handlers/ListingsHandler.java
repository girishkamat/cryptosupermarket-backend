package com.quikr.cryptosupermarket.handlers;

import com.quikr.cryptosupermarket.coinmarketcap.service.CoinMarketCapService;
import com.quikr.cryptosupermarket.domain.ListingsResult;
import com.quikr.cryptosupermarket.domain.ListingsWithPriceResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static java.lang.Integer.parseInt;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ListingsHandler {

    @Autowired
    private CoinMarketCapService coinMarketCapService;

    public Mono<ServerResponse> getListings(ServerRequest serverRequest) {
        return ok()
                .header("access-control-allow-origin","*")
                .contentType(APPLICATION_JSON)
                .body(Mono.just(coinMarketCapService.listings()), ListingsResult.class);
    }

    public Mono<ServerResponse> getListingsWithPrices(ServerRequest serverRequest) {
        int start = parseInt(serverRequest.queryParam("start").orElse("1"));
        int limit = parseInt(serverRequest.queryParam("limit").orElse("100"));
        String filter = serverRequest.queryParam("filter").orElse("");
        return ok()
                .header("access-control-allow-origin","*")
                .contentType(APPLICATION_JSON)
                .body(Mono.just(coinMarketCapService.listingsWithPrices(serverRequest.pathVariable("currency"), filter, start, limit)),
                        ListingsWithPriceResults.class);
    }
}
