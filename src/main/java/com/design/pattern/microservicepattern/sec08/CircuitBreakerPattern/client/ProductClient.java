package com.design.pattern.microservicepattern.sec08.CircuitBreakerPattern.client;

import com.design.pattern.microservicepattern.sec08.CircuitBreakerPattern.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${sec08.product.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<Product> getProduct(Integer id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(_ -> Mono.empty());
    }

}
