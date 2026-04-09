package com.capg.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.capg.springboot.dto.OrderRequestDTO;
import com.capg.springboot.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    //  Test API
    @GetMapping
    public String testOrderService() {
        return "Order Service is UP";
    }

    @PostMapping(value = "/place", consumes = "application/json")
    public String placeOrder(@RequestBody OrderRequestDTO request) {
        return service.placeOrder(request);
    }
}