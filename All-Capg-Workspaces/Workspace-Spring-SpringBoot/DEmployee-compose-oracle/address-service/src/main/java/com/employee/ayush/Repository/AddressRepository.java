package com.employee.ayush.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.ayush.Entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}