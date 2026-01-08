package com.design.pattern.microservicepattern.sec01.GatewayAggregator.dto;

import java.time.LocalDate;

public record PromotionResponse(Integer id, String type, Double discount, LocalDate endDate) {
}
