package com.capg.springboot.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.capg.springboot.entity.OrderEntity;
import com.capg.springboot.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repo;

    public OrderController(OrderRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public OrderEntity save(@RequestBody OrderEntity o) {
        return repo.save(o);
    }

    @GetMapping
    public List<OrderEntity> getAll() {
        return repo.findAll();
    }
}