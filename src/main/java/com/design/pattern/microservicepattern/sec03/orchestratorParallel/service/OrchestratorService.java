package com.design.pattern.microservicepattern.sec03.orchestratorParallel.service;

import com.design.pattern.microservicepattern.sec03.orchestratorParallel.client.ProductClient;
import com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto.*;
import com.design.pattern.microservicepattern.sec03.orchestratorParallel.util.DebugUtil;
import com.design.pattern.microservicepattern.sec03.orchestratorParallel.util.OrchestrationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OrchestratorService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderFulfillmentService fulfillmentService;

    @Autowired
    private OrderCancellationService cancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> mono) {
        return mono.map(OrchestrationRequestContext::new)
                .flatMap(this::getProduct)
                .doOnNext(OrchestrationUtil::buildRequestContext)
                .flatMap(fulfillmentService::placeOrder)
                .doOnNext(this::doOrderPostProcessing)
                .doOnNext(DebugUtil::print)
                .map(this::toOrderResponse);
    }

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext ctx) {
        return productClient.getProduct(ctx.getOrderRequest().getProductId())
                .map(Product::getPrice)
                .doOnNext(ctx::setProductPrice)
                .map(_ -> ctx);
    }

    private void doOrderPostProcessing(OrchestrationRequestContext ctx) {
        if (Status.FAILED.equals(ctx.getStatus())) {
            this.cancellationService.cancelOrder(ctx);
        }
    }

    private OrderResponse toOrderResponse(OrchestrationRequestContext ctx) {
        var isSuccess = Status.SUCCESS.equals(ctx.getStatus());
        var address = isSuccess ? ctx.getShippingResponse().getAddress() : null;
        var deliveryDate = isSuccess ? ctx.getShippingResponse().getExpectedDelivery() : null;
        return OrderResponse.create(
                ctx.getOrderRequest().getUserId(),
                ctx.getOrderRequest().getProductId(),
                ctx.getOrderId(),
                ctx.getStatus(),
                deliveryDate,
                address
        );
    }
}
