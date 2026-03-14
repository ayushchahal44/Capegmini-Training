package com.capg.springboot.service;

import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ProductService {

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackProduct")
    public String getProduct() {
        throw new RuntimeException("Product Service Down");
    }

    public String fallbackProduct(Throwable t) {
        return "Default Product : (Fallback Response)";
    }
}