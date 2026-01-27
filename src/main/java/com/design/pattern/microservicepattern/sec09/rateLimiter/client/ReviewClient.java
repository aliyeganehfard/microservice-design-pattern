package com.design.pattern.microservicepattern.sec09.rateLimiter.client;

import com.design.pattern.microservicepattern.sec09.rateLimiter.dto.Review;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ReviewClient {

    private final WebClient webClient;

    public ReviewClient(@Value("${sec09.review.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @RateLimiter(name = "review-service", fallbackMethod = "fallback")
    public Mono<List<Review>> getReviews(Integer id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, _ -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList();
    }

    public Mono<List<Review>> fallback(Integer id, Throwable ex) {
        return Mono.just(Collections.emptyList());
    }
}
