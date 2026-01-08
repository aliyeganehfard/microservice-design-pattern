package com.design.pattern.microservicepattern.sec01.GatewayAggregator.client;

import com.design.pattern.microservicepattern.sec01.GatewayAggregator.dto.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${sec01.product.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ProductResponse> getProduct(Integer id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .onErrorResume(_ -> Mono.empty());
    }

}
