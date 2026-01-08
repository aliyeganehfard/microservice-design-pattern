package com.design.pattern.microservicepattern.sec01.GatewayAggregator.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class Price {

    private Integer listPrice;
    private Double discount;
    private Double discountedPrice;
    private Double amountSaved;
    private LocalDate endDate;

}
