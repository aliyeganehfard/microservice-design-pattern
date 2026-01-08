package com.design.pattern.microservicepattern.sec02.scatterGather.controller;

import com.design.pattern.microservicepattern.sec02.scatterGather.dto.FlightResult;
import com.design.pattern.microservicepattern.sec02.scatterGather.service.FlightSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("sec02")
public class FlightController {

    @Autowired
    private FlightSearchService service;

    @GetMapping(value = "/flights/{from}/{to}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FlightResult> getFlights(@PathVariable String from, @PathVariable String to) {
        return service.getFlights(from, to);
    }
}
