package com.design.pattern.microservicepattern.sec02.scatterGather.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightResult {
    private String airline;
    private String from;
    private String to;
    private Double price;
    private LocalDate date;
}