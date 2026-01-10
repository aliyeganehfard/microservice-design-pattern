package com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OrderResponse {

    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private Status status;
    private String expectedDelivery;
    private Address shippingAddress;
}
