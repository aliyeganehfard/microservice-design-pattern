package com.design.pattern.microservicepattern.sec02.scatterGather.client;

import com.design.pattern.microservicepattern.sec02.scatterGather.dto.FlightResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class DeltaClient {

    private final WebClient webClient;

    public DeltaClient(@Value("${sec02.delta.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return webClient.get()
                .uri("{from}/{to}", from, to)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(_ -> Mono.empty());
    }
}
