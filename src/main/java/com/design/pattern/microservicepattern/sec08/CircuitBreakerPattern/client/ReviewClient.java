package com.design.pattern.microservicepattern.sec08.CircuitBreakerPattern.client;

import com.design.pattern.microservicepattern.sec08.CircuitBreakerPattern.dto.Review;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ReviewClient {

    private final WebClient webClient;

    public ReviewClient(@Value("${sec08.review.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @CircuitBreaker(name = "review-service", fallbackMethod = "fallbackReview")
    public Mono<List<Review>> getReviews(Integer id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, _ -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList()
                .retry(5)
                .timeout(Duration.ofMillis(300));
    }

    public Mono<List<Review>> fallbackReview(Integer id, Throwable ex) {
        log.info("fallback reviews called : {}", ex.getMessage());
        return Mono.just(Collections.emptyList());
    }
}
