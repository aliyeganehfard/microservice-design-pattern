package com.design.pattern.microservicepattern.sec05.splitter.service;

import com.design.pattern.microservicepattern.sec05.splitter.dto.ReservationItemRequest;
import com.design.pattern.microservicepattern.sec05.splitter.dto.ReservationItemResponse;
import com.design.pattern.microservicepattern.sec05.splitter.dto.ReservationType;
import reactor.core.publisher.Flux;

public abstract class ReservationHandler {


    protected abstract ReservationType getType();

    protected abstract Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux);
}
