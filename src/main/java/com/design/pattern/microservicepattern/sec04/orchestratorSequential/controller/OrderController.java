package com.design.pattern.microservicepattern.sec04.orchestratorSequential.controller;

import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.OrderRequest;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.OrderResponse;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.service.OrchestratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("sec04")
public class OrderController {

    @Autowired
    private OrchestratorService service;

    @PostMapping("/post")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> mono){
        return service.placeOrder(mono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/get")
    public Mono<ResponseEntity<OrderResponse>> getOrder(@RequestParam("userId") Integer userId,
                                                        @RequestParam("productId") Integer productId,
                                                        @RequestParam("quantity") Integer quantity){
        return placeOrder(Mono.just(OrderRequest.create(userId, productId, quantity)));
    }
}
