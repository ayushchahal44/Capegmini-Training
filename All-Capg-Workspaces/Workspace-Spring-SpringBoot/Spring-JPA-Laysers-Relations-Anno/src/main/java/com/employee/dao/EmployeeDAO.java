package com.employee.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.employee.entity.Employee;

@Repository
public class EmployeeDAO {

    @PersistenceContext
    private EntityManager em;

    public void save(Employee emp) {
        em.persist(emp);
    }

    public Employee find(int id) {
        return em.find(Employee.class, id);
    }

    public void delete(int id) {
        Employee e = em.find(Employee.class, id);
        if (e != null) em.remove(e);
    }
}