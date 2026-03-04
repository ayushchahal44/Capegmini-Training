package com.capg.springboot.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.capg.springboot.entity.Customer;

@Repository
@Transactional
public class CustomerDAOImpl implements CustomerDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addCustomer(Customer customer) {
        em.persist(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return em.createQuery("from Customer", Customer.class).getResultList();
    }

    @Override
    public Customer searchCustomer(int id) {
        return em.find(Customer.class, id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        em.merge(customer);
    }

    @Override
    public void deleteCustomer(int id) {

        Customer c = em.find(Customer.class, id);

        if (c != null) {
            em.remove(c);
        }
    }
}