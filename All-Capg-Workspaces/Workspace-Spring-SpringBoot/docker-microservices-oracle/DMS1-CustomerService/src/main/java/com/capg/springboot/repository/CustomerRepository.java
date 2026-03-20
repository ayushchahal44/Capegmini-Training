package com.capg.springboot.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.capg.springboot.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}