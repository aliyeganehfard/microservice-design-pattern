package com.design.pattern.microservicepattern.sec03.orchestratorParallel.service;

import com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto.OrchestrationRequestContext;
import com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderFulfillmentService {

    @Autowired
    private List<Orchestrator> orchestrators;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx) {

        var list = orchestrators.stream()
                .map(o -> o.create(ctx))
                .collect(Collectors.toList());

        return Mono.zip(list, a -> a[0])
                .cast(OrchestrationRequestContext.class)
                .doOnNext(this::updateStatus);
    }

    private void updateStatus(OrchestrationRequestContext ctx){
        var allSuccess = this.orchestrators.stream()
                .allMatch(o -> o.isSuccess().test(ctx));

        var status = allSuccess ? Status.SUCCESS : Status.FAILED;
        ctx.setStatus(status);
    }
}
