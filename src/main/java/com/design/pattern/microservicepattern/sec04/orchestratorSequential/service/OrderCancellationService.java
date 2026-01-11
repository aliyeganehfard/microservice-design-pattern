package com.design.pattern.microservicepattern.sec04.orchestratorSequential.service;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.OrchestrationRequestContext;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@Service
public class OrderCancellationService {

    private Sinks.Many<OrchestrationRequestContext> sink;
    private Flux<OrchestrationRequestContext> flux;

    @Autowired
    private List<Orchestrator> orchestrators;

    @PostConstruct
    private void init() {
        sink = Sinks.many().multicast().onBackpressureBuffer();
        flux = sink.asFlux().publishOn(Schedulers.boundedElastic());
        orchestrators.forEach(o -> flux.subscribe(o.cancel()));
    }

    public void cancelOrder(OrchestrationRequestContext ctx){
        this.sink.tryEmitNext(ctx);
    }
}
