package com.cg.jpastart.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "ADDRESS1")
public class Address1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ADDRESS_ID")
    private int addressId;

    @Column(name = "ADDRESS_STREET")
    private String street;

    @Column(name = "ADDRESS_CITY")
    private String city;

    @Column(name = "ADDRESS_STATE")
    private String state;

    @Column(name = "ADDRESS_ZIPCODE")
    private String zipCode;

    // Bi-Directional Relationship with Student1
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private Student1 student;

    // Getters and Setters
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Student1 getStudent() {
        return student;
    }

    public void setStudent(Student1 student) {
        this.student = student;
    }
}
