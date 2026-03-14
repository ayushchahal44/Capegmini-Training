package com.capg.springboot.controller;
 

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.capg.springboot.model.Product;

/**
 * Feign Client for PRODUCT-SERVICE
 */
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/product/{id}")
    Product getProduct(@PathVariable Long id);
}