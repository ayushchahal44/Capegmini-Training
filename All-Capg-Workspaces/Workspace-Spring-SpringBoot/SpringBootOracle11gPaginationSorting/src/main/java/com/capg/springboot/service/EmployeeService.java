package com.capg.springboot.service;

import com.capg.springboot.entity.Employee;
import com.capg.springboot.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public List<Employee> getEmployees(
            int page,
            int size,
            String sort,
            String dir
    ) {
        return repo.findEmployees(page, size, sort, dir);
    }
}