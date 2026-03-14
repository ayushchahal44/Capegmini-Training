package com.capg.springboot.controller;

 

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign Client for INVENTORY-SERVICE
 */
@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @GetMapping("/inventory/check/{productId}")
    boolean isStockAvailable(@PathVariable Long productId);
}