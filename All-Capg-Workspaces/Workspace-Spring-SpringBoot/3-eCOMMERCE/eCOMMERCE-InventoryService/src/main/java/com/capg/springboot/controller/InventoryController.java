package com.capg.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inventory Controller
 * Checks product stock availability
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    /**
     * Returns true if product is in stock
     */
    @GetMapping("/check/{productId}")
    public boolean checkStock(@PathVariable Long productId) {

        // Dummy logic: only productId 101 has stock
        return productId == 101;
    }
}