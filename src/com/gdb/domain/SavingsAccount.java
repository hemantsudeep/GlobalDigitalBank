package com.gdb.domain;

public class SavingsAccount extends Account {

    private double minBalance;
    private double interestRate;

    public SavingsAccount(String accountNumber, String name, int age,
                          double balance, String accountType,
                          String status, String pin,
                          double minBalance, double interestRate) {

        super(accountNumber, name, age, balance,
                accountType, status, pin);

        this.minBalance = minBalance;
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = balance * interestRate / 100;
        balance += interest;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }
}