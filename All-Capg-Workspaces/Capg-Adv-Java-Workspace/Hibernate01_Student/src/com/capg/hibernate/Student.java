package com.capg.hibernate;

import java.io.Serializable;

public class Student implements Serializable {

    private int sno;
    private String sname;
    private String email;
    private long mobile;

    // Default Constructor
    public Student() {
    }

    // Getters
    public int getSno() {
        return sno;
    }

    public String getSname() {
        return sname;
    }

    public String getEmail() {
        return email;
    }

    public long getMobile() {
        return mobile;
    }

    // Setters
    public void setSno(int sno) {
        this.sno = sno;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }
}
