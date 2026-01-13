package com.design.pattern.microservicepattern.sec05.splitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ReservationItemResponse {

    private UUID itemId;
    private ReservationType type;
    private String category;
    private String city;
    private LocalDate from;
    private LocalDate to;
    private Integer price;

}
