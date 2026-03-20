package com.employee.ayush.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.ayush.Entity.Address;
import com.employee.ayush.Service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService service;

    @PostMapping
    public Address save(@RequestBody Address address) {
        return service.save(address);
    }

    @GetMapping
    public List<Address> getAll() {
        return service.getAll();
    }
}