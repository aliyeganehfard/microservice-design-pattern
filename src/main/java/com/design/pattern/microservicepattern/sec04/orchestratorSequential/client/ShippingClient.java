package com.design.pattern.microservicepattern.sec04.orchestratorSequential.client;

import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.ShippingRequest;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.ShippingResponse;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ShippingClient {

    private final WebClient webClient;

    public ShippingClient(@Value("${sec04.shipping.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ShippingResponse> schedule(ShippingRequest request) {
        return callShippingService("schedule", request);
    }

    public Mono<ShippingResponse> cancel(ShippingRequest request) {
        return callShippingService("cancel", request);
    }

    private Mono<ShippingResponse> callShippingService(String endPoint, ShippingRequest request) {
        return webClient.post()
                .uri(endPoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(this.buildErrorResponse(request));
    }

    private ShippingResponse buildErrorResponse(ShippingRequest request) {
        return ShippingResponse.create(
                null,
                request.getQuantity(),
                Status.FAILED,
                null,
                null
        );
    }

}
