package com.capg.assessment.service;

import com.capg.assessment.entity.*;
import javax.persistence.*;

public class TicketService {

    private EntityManager em;

    public TicketService(EntityManager em) {
        this.em = em;
    }

    // Raise Ticket
    public void raiseTicket(Long orderId,
                            String issueDescription) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Order order = em.find(Order.class, orderId);

            SupportTicket ticket = new SupportTicket();
            ticket.setIssueDescription(issueDescription);
            ticket.setOrder(order);

            em.persist(ticket);

            tx.commit();
            System.out.println("Support Ticket Raised");

        } catch (Exception e) {
            tx.rollback();
        }
    }
}