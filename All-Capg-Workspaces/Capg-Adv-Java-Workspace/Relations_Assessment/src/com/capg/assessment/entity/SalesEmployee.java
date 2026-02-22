package com.capg.assessment.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "SALES_EMPLOYEE")
public class SalesEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "sales_emp_seq_gen")

    @SequenceGenerator(
            name = "sales_emp_seq_gen",
            sequenceName = "sales_emp_seq",
            allocationSize = 1
    )
    private Long id;

    private String name;
    private String department;

    // 🔹 One Employee → Many Leads
    @OneToMany(mappedBy = "employee",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<Lead> leads = new ArrayList<>();


    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Lead> getLeads() {
        return leads;
    }

    public void setLeads(List<Lead> leads) {
        this.leads = leads;
    }
}