package com.capg.assessment.entity;

import javax.persistence.*;

@Entity
@Table(name = "LEAD")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "lead_seq_gen")

    @SequenceGenerator(
            name = "lead_seq_gen",
            sequenceName = "lead_seq",
            allocationSize = 1
    )
    private Long id;

    private String name;
    private String source;
    private String contactInfo;

    // 🔹 Many Leads → One SalesEmployee
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private SalesEmployee employee;

    // 🔹 One Lead → One Customer
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public SalesEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(SalesEmployee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}