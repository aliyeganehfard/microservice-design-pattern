package com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Address {

    private String street;
    private String city;
    private String state;
    private String zipCode;
}
