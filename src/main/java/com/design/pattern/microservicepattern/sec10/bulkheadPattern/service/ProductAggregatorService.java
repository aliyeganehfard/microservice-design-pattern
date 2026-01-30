package com.design.pattern.microservicepattern.sec10.bulkheadPattern.service;

import com.design.pattern.microservicepattern.sec10.bulkheadPattern.client.ProductClient;
import com.design.pattern.microservicepattern.sec10.bulkheadPattern.client.ReviewClient;
import com.design.pattern.microservicepattern.sec10.bulkheadPattern.dto.Product;
import com.design.pattern.microservicepattern.sec10.bulkheadPattern.dto.ProductAggregate;
import com.design.pattern.microservicepattern.sec10.bulkheadPattern.dto.Review;
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
    private ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id) {
       return Mono.zip(
                productClient.getProduct(id),
                reviewClient.getReviews(id)
        ).map(t -> toDto(t.getT1(), t.getT2()));
    }

    private ProductAggregate toDto(Product product, List<Review> reviews) {
        var aggregate = new ProductAggregate();
        aggregate.setId(product.id());
        aggregate.setCategory(product.category());
        aggregate.setDescription(product.description());
        aggregate.setReviews(reviews);
        return aggregate;
    }
}
