package com.capg.assessment.entity;

import javax.persistence.*;

@Entity
@Table(name = "SUPPORT_TICKET")
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "ticket_seq_gen")

    @SequenceGenerator(
            name = "ticket_seq_gen",
            sequenceName = "ticket_seq",
            allocationSize = 1
    )
    private Long id;

    private String issueDescription;

    // 🔹 One Ticket → One Order
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;


    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}