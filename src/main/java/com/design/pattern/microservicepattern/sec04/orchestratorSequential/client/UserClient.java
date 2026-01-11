package com.design.pattern.microservicepattern.sec04.orchestratorSequential.client;

import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.PaymentRequest;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.PaymentResponse;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${sec04.user.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PaymentResponse> deduct(PaymentRequest request) {
        return callUserService("deduct", request);
    }

    public Mono<PaymentResponse> refund(PaymentRequest request) {
        return callUserService("refund", request);
    }

    private Mono<PaymentResponse> callUserService(String deduct, PaymentRequest request) {
        return webClient.post()
                .uri(deduct)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .onErrorReturn(this.buildErrorResponse(request));
    }

    private PaymentResponse buildErrorResponse(PaymentRequest request) {
        return PaymentResponse.create(
                null,
                request.getUserId(),
                null,
                request.getAmount(),
                Status.FAILED
        );
    }

}
