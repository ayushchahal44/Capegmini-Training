package com.capg.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.capg.springboot.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}