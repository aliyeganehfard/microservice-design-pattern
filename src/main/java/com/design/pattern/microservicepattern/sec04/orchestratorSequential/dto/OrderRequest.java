package com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OrderRequest {

    private Integer userId;

    private Integer productId;

    private Integer quantity;
}
