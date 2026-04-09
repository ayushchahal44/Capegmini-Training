package com.capg.assessment.service;

import com.capg.assessment.entity.*;
import javax.persistence.*;

public class LeadService {

    private EntityManager em;

    public LeadService(EntityManager em) {
        this.em = em;
    }

    // 1️⃣ Create Lead
    public void createLead(String name, String source, String contactInfo) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Lead lead = new Lead();
            lead.setName(name);
            lead.setSource(source);
            lead.setContactInfo(contactInfo);

            em.persist(lead);

            tx.commit();
            System.out.println("Lead Created");
        } catch (Exception e) {
            tx.rollback();
        }
    }

    // 2️⃣ Assign Lead
    public void assignLeadToEmployee(Long leadId, Long empId) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Lead lead = em.find(Lead.class, leadId);
            SalesEmployee emp = em.find(SalesEmployee.class, empId);

            lead.setEmployee(emp);

            tx.commit();
            System.out.println("Lead Assigned");
        } catch (Exception e) {
            tx.rollback();
        }
    }

    // 3️⃣ Convert Lead → Customer
    public void convertLeadToCustomer(Long leadId) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Lead lead = em.find(Lead.class, leadId);

            Customer c = new Customer();
            c.setName(lead.getName());
            c.setEmail(lead.getContactInfo());
            c.setPhone("Converted");

            em.persist(c);

            lead.setCustomer(c);

            tx.commit();
            System.out.println("Lead Converted to Customer");
        } catch (Exception e) {
            tx.rollback();
        }
    }
}