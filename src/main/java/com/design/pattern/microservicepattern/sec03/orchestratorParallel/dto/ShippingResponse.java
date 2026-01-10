package com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ShippingResponse {

    private UUID orderId;
    private Integer quantity;
    private Status status;
    private String expectedDelivery;
    private Address address;
}
