package com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class InventoryRequest{

    private UUID paymentId;
    private Integer productId;
    private Integer quantity;

}
