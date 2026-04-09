package com.capg.assessment.service;

import com.capg.assessment.entity.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

public class OrderService {

    private EntityManager em;

    public OrderService(EntityManager em) {
        this.em = em;
    }

    // Place Order
    public void placeOrder(Long customerId, List<Long> productIds) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Customer customer =
                em.find(Customer.class, customerId);

            Order order = new Order();
            order.setCustomer(customer);
            order.setOrderDate(LocalDate.now().toString());

            List<Product> products = new ArrayList<>();
            double total = 0;

            for (Long id : productIds) {

                Product p = em.find(Product.class, id);
                products.add(p);
                total += p.getPrice();
            }

            order.setProducts(products);
            order.setTotalAmount(total);

            em.persist(order);

            tx.commit();
            System.out.println("Order Placed. Total = " + total);

        } catch (Exception e) {
            tx.rollback();
        }
    }
}