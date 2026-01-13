package com.design.pattern.microservicepattern.sec05.splitter.client;

import com.design.pattern.microservicepattern.sec05.splitter.dto.RoomReservationRequest;
import com.design.pattern.microservicepattern.sec05.splitter.dto.RoomReservationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RoomClient {

    private final WebClient webClient;

    public RoomClient(@Value("${sec05.room.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<RoomReservationResponse> reserve(Flux<RoomReservationRequest> flux) {
        return webClient
                .post()
                .body(flux, RoomReservationRequest.class)
                .retrieve()
                .bodyToFlux(RoomReservationResponse.class)
                .onErrorResume(_ -> Mono.empty());
    }
}
