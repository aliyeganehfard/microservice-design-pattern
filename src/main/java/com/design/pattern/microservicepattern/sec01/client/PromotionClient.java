package com.design.pattern.microservicepattern.sec01.client;

import com.design.pattern.microservicepattern.sec01.dto.ProductResponse;
import com.design.pattern.microservicepattern.sec01.dto.PromotionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Service
public class PromotionClient {

    private final PromotionResponse noPromotion = new PromotionResponse(
            0, "no promotion", 0d, LocalDate.now()
    );

    private final WebClient webClient;

    public PromotionClient(@Value("${sec01.promotion.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PromotionResponse> getPromotion(Integer id){
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(PromotionResponse.class)
                .onErrorReturn(noPromotion);
    }


}
