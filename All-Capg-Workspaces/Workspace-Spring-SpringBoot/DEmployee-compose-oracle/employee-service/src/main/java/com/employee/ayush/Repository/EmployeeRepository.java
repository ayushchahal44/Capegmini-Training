package com.employee.ayush.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.ayush.Entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}