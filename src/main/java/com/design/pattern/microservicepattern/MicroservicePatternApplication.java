package com.design.pattern.microservicepattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.design.pattern.microservicepattern.sec01")
public class MicroservicePatternApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicePatternApplication.class, args);
    }

}
