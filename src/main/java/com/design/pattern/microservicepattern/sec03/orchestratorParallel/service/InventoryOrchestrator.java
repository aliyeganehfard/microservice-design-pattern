package com.design.pattern.microservicepattern.sec03.orchestratorParallel.service;

import com.design.pattern.microservicepattern.sec03.orchestratorParallel.client.InventoryClient;
import com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto.OrchestrationRequestContext;
import com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Slf4j
@Service
public class InventoryOrchestrator extends Orchestrator {

    @Autowired
    private InventoryClient inventoryClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return inventoryClient.deduct(ctx.getInventoryRequest())
                .doOnNext(ctx::setInventoryResponse)
                .thenReturn(ctx);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> Status.SUCCESS.equals(ctx.getInventoryResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getInventoryRequest)
                .flatMap(inventoryClient::restore)
                .subscribe();
    }
}
