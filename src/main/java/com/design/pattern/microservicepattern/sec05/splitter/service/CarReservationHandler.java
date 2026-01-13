package com.design.pattern.microservicepattern.sec05.splitter.service;

import com.design.pattern.microservicepattern.sec05.splitter.client.CarClient;
import com.design.pattern.microservicepattern.sec05.splitter.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class CarReservationHandler extends ReservationHandler{

    @Autowired
    private CarClient client;

    @Override
    protected ReservationType getType() {
        return ReservationType.CAR;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux) {
        return flux.map(this::toCarRequest)
                .transform(client::reserve)
                .map(this::toResponse);
    }

    private CarReservationRequest toCarRequest(ReservationItemRequest request){
        return CarReservationRequest.create(
          request.getCity(),
          request.getFrom(),
          request.getTo(),
          request.getCategory()
        );
    }

    private ReservationItemResponse toResponse(CarReservationResponse response){
        return ReservationItemResponse.create(
          response.getReservationId(),
          getType(),
          response.getCategory(),
          response.getCity(),
          response.getPickup(),
          response.getDrop(),
          response.getPrice()
        );
    }
}
