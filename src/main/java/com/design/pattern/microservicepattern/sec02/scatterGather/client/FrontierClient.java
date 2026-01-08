package com.design.pattern.microservicepattern.sec02.scatterGather.client;

import com.design.pattern.microservicepattern.sec02.scatterGather.dto.FlightResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FrontierClient {

    private final WebClient webClient;

    public FrontierClient(@Value("${sec02.frontier.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return webClient.post()
                .bodyValue(FrontierRequest.create(from, to))
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(_ -> Mono.empty());
    }

    @Data
    @ToString
    @AllArgsConstructor(staticName = "create")
    private static class FrontierRequest {
        private String from;
        private String to;
    }
}
