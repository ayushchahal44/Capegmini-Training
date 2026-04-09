package com.capg.assessment.Bean;

import java.io.Serializable;

public class BankBean implements Serializable {

    private int accno;
    private String name;
    private long mobile;
    private double balance;

    public int getAccno() { return accno; }
    public void setAccno(int accno) { this.accno = accno; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getMobile() { return mobile; }
    public void setMobile(long mobile) { this.mobile = mobile; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
