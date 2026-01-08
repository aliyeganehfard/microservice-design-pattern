package com.design.pattern.microservicepattern.sec01.GatewayAggregator.service;

import com.design.pattern.microservicepattern.sec01.GatewayAggregator.client.ProductClient;
import com.design.pattern.microservicepattern.sec01.GatewayAggregator.client.PromotionClient;
import com.design.pattern.microservicepattern.sec01.GatewayAggregator.client.ReviewClient;
import com.design.pattern.microservicepattern.sec01.GatewayAggregator.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class ProductAggregatorService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PromotionClient promotionClient;

    @Autowired
    private ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id) {
       return Mono.zip(
                productClient.getProduct(id),
                promotionClient.getPromotion(id),
                reviewClient.getReviews(id)
        ).map(t -> toDto(t.getT1(), t.getT2(), t.getT3()));
    }

    private ProductAggregate toDto(ProductResponse product, PromotionResponse promotion, List<Review> reviews) {
        var price = new Price();
        var amountSaved = product.price() * promotion.discount() / 100;
        var discountedPrice = product.price() - amountSaved;

        price.setListPrice(product.price());
        price.setAmountSaved(amountSaved);
        price.setDiscountedPrice(discountedPrice);
        price.setDiscount(promotion.discount());
        price.setEndDate(promotion.endDate());

        var aggregate = new ProductAggregate();
        aggregate.setId(product.id());
        aggregate.setCategory(product.category());
        aggregate.setDescription(product.description());
        aggregate.setPrice(price);
        aggregate.setReviews(reviews);

        return aggregate;
    }
}
