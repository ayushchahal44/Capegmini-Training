package com.capg.spring;

public class EmployeeServiceImpl implements EmployeeService {

    String name;
    double salary;
    String city;
    String dept;

    // Default Constructor (for Setter Injection)
    public EmployeeServiceImpl() {
    }

    // Parameterized Constructor (for Constructor Injection)
    public EmployeeServiceImpl(String name, double salary, String city, String dept) {
        this.name = name;
        this.salary = salary;
        this.city = city;
        this.dept = dept;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    // Business Method
    public void allEmployees() {
        System.out.println(
            "Name : " + name +
            ", Salary : " + salary +
            ", City : " + city +
            ", Dept : " + dept
        );
    }
}
