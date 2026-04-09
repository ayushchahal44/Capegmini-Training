package com.capg.springboot.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.capg.springboot.entity.Customer;
import com.capg.springboot.repository.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Customer save(@RequestBody Customer c) {
        return repo.save(c);
    }

    @GetMapping
    public List<Customer> getAll() {
        return repo.findAll();
    }
}