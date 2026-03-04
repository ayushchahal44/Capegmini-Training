package com.capg.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.capg.springboot.entity.Customer;
import com.capg.springboot.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("/add")
    public String addCustomer(@RequestBody Customer customer) {

        service.addCustomer(customer);

        return "Customer Added Successfully";
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {

        return service.getAllCustomers();
    }

    @GetMapping("/search/{id}")
    public Customer searchCustomer(@PathVariable int id) {

        return service.searchCustomer(id);
    }

    @PutMapping("/update")
    public String updateCustomer(@RequestBody Customer customer) {

        service.updateCustomer(customer);

        return "Customer Updated Successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable int id) {

        service.deleteCustomer(id);

        return "Customer Deleted Successfully";
    }
}