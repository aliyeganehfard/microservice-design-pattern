package com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto;

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
