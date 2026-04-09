package com.capg.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.capg.springboot.model.Product;

/**
 * Order Controller
 * Handles order placement
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/place/{productId}")
    public String placeOrder(@PathVariable Long productId) {
        return "Order service working for product " + productId;
    }
}