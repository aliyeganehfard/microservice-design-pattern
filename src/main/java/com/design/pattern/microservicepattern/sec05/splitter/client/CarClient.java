package com.design.pattern.microservicepattern.sec05.splitter.client;

import com.design.pattern.microservicepattern.sec05.splitter.dto.CarReservationRequest;
import com.design.pattern.microservicepattern.sec05.splitter.dto.CarReservationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CarClient {

    private final WebClient webClient;

    public CarClient(@Value("${sec05.car.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<CarReservationResponse> reserve(Flux<CarReservationRequest> flux) {
        return webClient
                .post()
                .body(flux, CarReservationRequest.class)
                .retrieve()
                .bodyToFlux(CarReservationResponse.class)
                .onErrorResume(_ -> Mono.empty());
    }
}
