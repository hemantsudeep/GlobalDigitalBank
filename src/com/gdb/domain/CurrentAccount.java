package com.gdb.domain;

public class CurrentAccount extends Account {

    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String name, int age,
                          double balance, String accountType,
                          String status, String pin,
                          double overdraftLimit) {

        super(accountNumber, name, age, balance,
                accountType, status, pin);

        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}