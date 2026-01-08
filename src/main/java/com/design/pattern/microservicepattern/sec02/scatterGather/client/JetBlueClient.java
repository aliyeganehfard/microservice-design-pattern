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
public class JetBlueClient {

    private final WebClient webClient;

    private static final String JETBLUE = "JETBLUE";

    public JetBlueClient(@Value("${sec02.jetblue.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return webClient.get()
                .uri("{from}/{to}", from, to)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .doOnNext(fr -> normalizeResponse(fr, from, to))
                .onErrorResume(_ -> Mono.empty());
    }

    private void normalizeResponse(FlightResult result, String from, String to) {
        result.setFrom(from);
        result.setTo(to);
        result.setAirline(JETBLUE);
    }
}
