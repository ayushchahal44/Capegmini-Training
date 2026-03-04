package com.capg.springboot.dao;

import java.util.List;
import com.capg.springboot.entity.Customer;

public interface CustomerDAO {

    void addCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer searchCustomer(int id);

    void updateCustomer(Customer customer);

    void deleteCustomer(int id);
}