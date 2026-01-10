package com.design.pattern.microservicepattern.sec03.orchestratorParallel.client;

import com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class InventoryClient {

    private final WebClient webClient;

    public InventoryClient(@Value("${sec03.inventory.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<InventoryResponse> deduct(InventoryRequest request) {
        return callInventoryService("deduct", request);
    }

    public Mono<InventoryResponse> restore(InventoryRequest request) {
        return callInventoryService("restore", request);
    }

    private Mono<InventoryResponse> callInventoryService(String endPoint, InventoryRequest request) {
        return webClient.post()
                .uri(endPoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .onErrorReturn(this.buildErrorResponse(request));
    }

    private InventoryResponse buildErrorResponse(InventoryRequest request) {
        return InventoryResponse.create(
                request.getProductId(),
                request.getQuantity(),
                null,
                Status.FAILED
        );
    }

}
