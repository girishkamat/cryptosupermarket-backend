package com.quikr.cryptosupermarket.config;

import com.quikr.cryptosupermarket.handlers.ListingsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Autowired
    private ListingsHandler listingsHandler;

    @Bean
    public RouterFunction<?> routes() {
        return route(GET("/listings"), listingsHandler::getListings)
                .andRoute(GET("/listings/quotes/{currency}"), listingsHandler::getListingsWithPrices);
    }

}
