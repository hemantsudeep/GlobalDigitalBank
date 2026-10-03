package com.gdb.domain;

import com.gdb.exceptions.*;

public class SavingsAccount extends AbstractAccount {

    private int tenureYears;
    private double minBalance;
    private double interestRate;

    // Existing constructor retained for compatibility
    public SavingsAccount(String accountNumber,
                          String name,
                          int age,
                          double balance,
                          String status,
                          String pin) {

        this(accountNumber, name, age, balance, status, pin, 0);
    }

    // Existing constructor retained
    public SavingsAccount(String accountNumber,
                          String name,
                          int age,
                          double balance,
                          String status,
                          String pin,
                          double minBalance,
                          double interestRate) {

        super(accountNumber,
                name,
                age,
                balance,
                "SAVINGS",
                status,
                pin);

        this.tenureYears = 0;
        this.minBalance = minBalance;
        this.interestRate = interestRate;
    }

    // New Activity 13.2 constructor
    public SavingsAccount(String accountNumber,
                          String name,
                          int age,
                          double balance,
                          String status,
                          String pin,
                          int tenureYears) {

        super(accountNumber,
                name,
                age,
                balance,
                "SAVINGS",
                status,
                pin);

        this.tenureYears = tenureYears;

        this.minBalance =
                AccountRulesEngine.getSavingsMinBalance(tenureYears);

        this.interestRate =
                AccountRulesEngine.getSavingsInterestRate(tenureYears);
    }

    @Override
    public void processDebit(double amount)
            throws AccountException {

        if ((balance - amount) < minBalance) {

            throw new MinimumBalanceViolationException(
                    "Cannot breach minimum balance of Rs "
                            + minBalance);
        }

        balance -= amount;
    }

    public void applyInterest() {

        double interest =
                this.balance * (interestRate / 100.0);

        this.balance += interest;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getTenureYears() {
        return tenureYears;
    }
}