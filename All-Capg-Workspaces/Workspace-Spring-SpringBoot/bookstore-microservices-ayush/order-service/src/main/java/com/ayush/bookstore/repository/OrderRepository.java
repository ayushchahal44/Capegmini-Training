package com.ayush.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.bookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}