package com.design.pattern.microservicepattern.sec03.orchestratorParallel.service;

import com.design.pattern.microservicepattern.sec03.orchestratorParallel.client.UserClient;
import com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto.OrchestrationRequestContext;
import com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class PaymentOrchestrator extends Orchestrator {

    @Autowired
    private UserClient client;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return client.deduct(ctx.getPaymentRequest())
                .doOnNext(ctx::setPaymentResponse)
                .thenReturn(ctx);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> Status.SUCCESS.equals(ctx.getPaymentResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getPaymentRequest)
                .flatMap(client::refund)
                .subscribe();
    }
}
