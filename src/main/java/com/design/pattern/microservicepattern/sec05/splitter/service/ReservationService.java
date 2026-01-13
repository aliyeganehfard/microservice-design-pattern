package com.design.pattern.microservicepattern.sec05.splitter.service;

import com.design.pattern.microservicepattern.sec05.splitter.dto.ReservationItemRequest;
import com.design.pattern.microservicepattern.sec05.splitter.dto.ReservationItemResponse;
import com.design.pattern.microservicepattern.sec05.splitter.dto.ReservationResponse;
import com.design.pattern.microservicepattern.sec05.splitter.dto.ReservationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservationService {

    private final Map<ReservationType, ReservationHandler> map;

    public ReservationService(List<ReservationHandler> handlers) {
        map = handlers.stream()
                .collect(Collectors.toMap(ReservationHandler::getType, Function.identity()));
    }

    public Mono<ReservationResponse> reserve(Flux<ReservationItemRequest> flux){
        return flux.groupBy(ReservationItemRequest::getType)
                .flatMap(this::aggregator)
                .collectList()
                .map(this::toResponse);
    }

    private Flux<ReservationItemResponse> aggregator(GroupedFlux<ReservationType, ReservationItemRequest> groupedFlux){
       var key = groupedFlux.key();
       var handler = map.get(key);
       return handler.reserve(groupedFlux);
    }

    private ReservationResponse toResponse(List<ReservationItemResponse> list) {
        return ReservationResponse.create(
                UUID.randomUUID(),
                list.stream().mapToInt(ReservationItemResponse::getPrice).sum(),
                list
        );
    }
}
