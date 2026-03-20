package com.employee.ayush.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.ayush.Entity.Address;
import com.employee.ayush.Repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repo;

    public Address save(Address address) {
        return repo.save(address);
    }

    public List<Address> getAll() {
        return repo.findAll();
    }
}