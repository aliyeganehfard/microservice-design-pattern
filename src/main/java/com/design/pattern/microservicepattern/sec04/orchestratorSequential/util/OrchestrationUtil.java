package com.design.pattern.microservicepattern.sec04.orchestratorSequential.util;

import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.InventoryRequest;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.OrchestrationRequestContext;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.PaymentRequest;
import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.ShippingRequest;

public class OrchestrationUtil {

    public static void buildPaymentRequest(OrchestrationRequestContext ctx) {
        var paymentRequest = PaymentRequest.create(
                ctx.getOrderRequest().getUserId(),
                ctx.getProductPrice() * ctx.getOrderRequest().getQuantity(),
                ctx.getOrderId()
        );
        ctx.setPaymentRequest(paymentRequest);
    }

    public static void buildInventoryRequest(OrchestrationRequestContext ctx) {
        var inventoryRequest = InventoryRequest.create(
                ctx.getPaymentResponse().getPaymentId(),
                ctx.getOrderRequest().getProductId(),
                ctx.getOrderRequest().getQuantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    public static void buildShippingRequest(OrchestrationRequestContext ctx) {
        var shippingRequest = ShippingRequest.create(
                ctx.getOrderRequest().getQuantity(),
                ctx.getOrderRequest().getUserId(),
                ctx.getInventoryResponse().getInventoryId()
        );
        ctx.setShippingRequest(shippingRequest);
    }
}
