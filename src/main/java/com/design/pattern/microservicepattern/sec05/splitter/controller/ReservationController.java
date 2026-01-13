package com.design.pattern.microservicepattern.sec05.splitter.controller;

import com.design.pattern.microservicepattern.sec05.splitter.dto.ReservationItemRequest;
import com.design.pattern.microservicepattern.sec05.splitter.dto.ReservationResponse;
import com.design.pattern.microservicepattern.sec05.splitter.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("sec05")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/reserve")
    public Mono<ReservationResponse> reserve(@RequestBody Flux<ReservationItemRequest> reservationItemRequest) {
        return reservationService.reserve(reservationItemRequest);
    }
}
