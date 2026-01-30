package com.design.pattern.microservicepattern.sec10.bulkheadPattern.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping("sec10")
public class FibController {

    private final Scheduler scheduler = Schedulers.newParallel("fib", 6);

    // suppose
    // CPU intensive
    @GetMapping("fib/{input}")
    public Mono<ResponseEntity<Long>> doubleInput(@PathVariable("input") Long input) {
        return Mono.fromSupplier(() -> findFib(input))
                .subscribeOn(scheduler)
                .map(ResponseEntity::ok);
    }

    private Long findFib(Long input) {
        if (input < 2) {
            return input;
        }
        return findFib(input - 1) + findFib(input - 2);
    }
}
