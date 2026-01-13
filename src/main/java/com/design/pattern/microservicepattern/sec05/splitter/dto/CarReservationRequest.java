package com.design.pattern.microservicepattern.sec05.splitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CarReservationRequest {

    private String city;
    private LocalDate pickup;
    private LocalDate drop;
    private String category;

}
