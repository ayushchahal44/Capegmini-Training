package com.example.order.client;

 

import com.example.order.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign with DIRECT URL (no Eureka).
 * The base URL is configured in application.properties: product.service.url
 */
@FeignClient(name = "productClient", url = "${product.service.url}")
public interface ProductClient {

    @GetMapping("/products/{id}")
    Product getById(@PathVariable("id") String id);
}
