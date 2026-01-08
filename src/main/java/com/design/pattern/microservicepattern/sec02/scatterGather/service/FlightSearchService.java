package com.design.pattern.microservicepattern.sec02.scatterGather.service;

import com.design.pattern.microservicepattern.sec02.scatterGather.client.DeltaClient;
import com.design.pattern.microservicepattern.sec02.scatterGather.client.FrontierClient;
import com.design.pattern.microservicepattern.sec02.scatterGather.client.JetBlueClient;
import com.design.pattern.microservicepattern.sec02.scatterGather.dto.FlightResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@Service
public class FlightSearchService {

    @Autowired
    private DeltaClient deltaClient;

    @Autowired
    private FrontierClient frontierClient;

    @Autowired
    private JetBlueClient jetBlueClient;

    public Flux<FlightResult> getFlights(String from, String to) {
        return Flux.merge(
                deltaClient.getFlights(from, to),
                frontierClient.getFlights(from, to),
                jetBlueClient.getFlights(from, to)
        ).take(Duration.ofSeconds(10));
    }
}
