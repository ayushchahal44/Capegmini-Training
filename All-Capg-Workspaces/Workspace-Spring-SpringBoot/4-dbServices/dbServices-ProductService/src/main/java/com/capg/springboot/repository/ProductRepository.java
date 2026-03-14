package com.capg.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capg.springboot.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // No code needed – CRUD methods auto-generated
}