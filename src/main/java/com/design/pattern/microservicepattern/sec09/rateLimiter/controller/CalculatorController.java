package com.design.pattern.microservicepattern.sec09.rateLimiter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("sec09")
public class CalculatorController {

    // suppose
    // CPU intensive
    // 5 requests / 20 seconds
    @GetMapping("calculator/{input}")
    public Mono<ResponseEntity<Integer>> doubleInput(@PathVariable("input") Integer input) {
        return Mono.fromSupplier(() -> input * 2)
                .map(ResponseEntity::ok);
    }
}
