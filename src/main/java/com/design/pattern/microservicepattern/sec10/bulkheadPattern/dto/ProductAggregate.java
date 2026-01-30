package com.design.pattern.microservicepattern.sec10.bulkheadPattern.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProductAggregate {

    private Integer id;
    private String category;
    private String description;
    private List<Review> reviews;
}
