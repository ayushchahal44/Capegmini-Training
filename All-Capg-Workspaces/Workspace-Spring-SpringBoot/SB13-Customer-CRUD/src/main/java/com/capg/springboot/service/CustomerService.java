package com.capg.springboot.service;

import java.util.List;
import com.capg.springboot.entity.Customer;

public interface CustomerService {

    void addCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer searchCustomer(int id);

    void updateCustomer(Customer customer);

    void deleteCustomer(int id);
}