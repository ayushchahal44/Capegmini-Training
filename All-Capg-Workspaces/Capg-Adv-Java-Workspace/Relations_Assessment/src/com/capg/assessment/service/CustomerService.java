package com.capg.assessment.service;

import com.capg.assessment.entity.*;
import javax.persistence.*;

public class CustomerService {

    private EntityManager em;

    public CustomerService(EntityManager em) {
        this.em = em;
    }

    public void registerCustomer(String name, String email, String phone) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Customer c = new Customer();
            c.setName(name);
            c.setEmail(email);
            c.setPhone(phone);

            em.persist(c);

            tx.commit();
            System.out.println("Customer Registered");
        } catch (Exception e) {
            tx.rollback();
        }
    }
}