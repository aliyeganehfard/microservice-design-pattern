package com.design.pattern.microservicepattern.sec10.bulkheadPattern.controller;

import com.design.pattern.microservicepattern.sec10.bulkheadPattern.dto.ProductAggregate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
class FibControllerTest {

    private static WebClient webClient;

    @BeforeAll
    public static void setup() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080/sec10/").build();
    }

    @Test
    public void concurrentFib() {
        StepVerifier.create(Flux.merge(fibRequests(), productRequests()))
                .verifyComplete();
    }

    private Mono<Void> fibRequests() {
        return Flux.range(1, 10)
                .flatMap(i -> webClient.get().uri("fib/45").retrieve().bodyToMono(Long.class))
                .doOnNext(this::print)
                .then();
    }

    private Mono<Void> productRequests() {
        return Mono.delay(Duration.ofMillis(100))
                .thenMany(Flux.range(1, 10))
                .flatMap(i -> webClient.get().uri("product/1").retrieve().bodyToMono(ProductAggregate.class))
                .map(ProductAggregate::getCategory)
                .doOnNext(this::print)
                .then();
    }

    private void print(Object o) {
        System.out.println(LocalDateTime.now() + " " + o);
    }
}