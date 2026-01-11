package com.design.pattern.microservicepattern.sec04.orchestratorSequential.client;

import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.InventoryRequest;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.InventoryResponse;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class InventoryClient {

    private final WebClient webClient;

    public InventoryClient(@Value("${sec04.inventory.service}") String baseUrl) {
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
                null,
                request.getProductId(),
                request.getQuantity(),
                null,
                Status.FAILED
        );
    }

}
