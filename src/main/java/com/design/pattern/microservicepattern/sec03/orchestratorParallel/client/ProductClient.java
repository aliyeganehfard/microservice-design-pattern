package com.design.pattern.microservicepattern.sec03.orchestratorParallel.client;
import com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${sec03.product.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<Product> getProduct(Integer id) {
        return webClient.get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .onErrorResume(_ -> Mono.empty());
    }
}
