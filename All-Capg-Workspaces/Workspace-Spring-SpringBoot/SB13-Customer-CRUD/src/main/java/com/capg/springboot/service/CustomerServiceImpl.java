package com.capg.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.springboot.dao.CustomerDAO;
import com.capg.springboot.entity.Customer;
import com.capg.springboot.exception.CustomerNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO dao;

    @Override
    public void addCustomer(Customer customer) {
        dao.addCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return dao.getAllCustomers();
    }

    @Override
    public Customer searchCustomer(int id) {

        Customer c = dao.searchCustomer(id);

        if (c == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        return c;
    }

    @Override
    public void updateCustomer(Customer customer) {

        Customer existing = dao.searchCustomer(customer.getCustid());

        if (existing == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        dao.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(int id) {

        Customer c = dao.searchCustomer(id);

        if (c == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        dao.deleteCustomer(id);
    }
}