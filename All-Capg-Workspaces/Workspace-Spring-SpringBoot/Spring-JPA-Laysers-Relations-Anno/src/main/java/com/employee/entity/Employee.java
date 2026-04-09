package com.employee.entity;

 

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "emp_id")
    private int empId;

    private String empName;
    private double salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addr_id")
    private Address address;

    // getters & setters
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }

    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}