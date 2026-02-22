package com.cg.jpastart.entities;

import java.io.Serializable;

import javax.persistence.*;
/*
 Defines the owning side of the relationship.
@JoinColumn establishes the foreign key.
 */
@Entity
@Table(name = "STUDENTS1")
public class Student1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STUDENT_ID")
    private int studentId;

    @Column(name = "STUDENT_NAME")
    private String name;

    // Cascade ensures dependent entity is saved/deleted automatically
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID") // Foreign key column
    private Address1 address;

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address1 getAddress() {
        return address;
    }

    public void setAddress(Address1 address) {
        this.address = address;
    }
}
