package com.design.pattern.microservicepattern.sec09.rateLimiter.client;

import com.design.pattern.microservicepattern.sec09.rateLimiter.dto.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
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

    public Mono<List<Review>> getReviews(Integer id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, _ -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList()
//                .retry(5)
                .retryWhen(
                        Retry.
                                fixedDelay(5, Duration.ofMillis(1000))
                                .doBeforeRetry(rs -> log.info("retry {}", rs.totalRetries()))
                )
                .timeout(Duration.ofSeconds(7))
                .onErrorReturn(Collections.emptyList());
    }
}
