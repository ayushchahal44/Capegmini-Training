package com.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.dao.EmployeeDAO;
import com.employee.entity.Employee;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeDAO dao;

    public void add(Employee e) {
        dao.save(e);
    }

    public Employee get(int id) {
        return dao.find(id);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}