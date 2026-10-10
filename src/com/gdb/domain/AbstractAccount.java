
package com.gdb.domain;

import com.gdb.exceptions.*;
import java.time.LocalDate;

public abstract class AbstractAccount implements IAccount {

    protected String accountNumber;
    protected String name;
    protected int age;
    protected double balance;
    protected String accountType;
    protected String status;
    protected String pin;

    private double dailyTransferTotal = 0.0;
    private LocalDate lastTransferDate = LocalDate.now();
    protected int tenureYears = 0;

    public AbstractAccount(
            String accountNumber,
            String name,
            int age,
            double balance,
            String accountType,
            String status,
            String pin) {

        if (age < 18) {
            throw new IllegalArgumentException(
                    "Customer age must be 18 or above");
        }

        if (balance < 0) {
            throw new IllegalArgumentException(
                    "Initial balance cannot be negative");
        }

        if (pin == null || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException(
                    "PIN must be 4 digits");
        }

        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = balance;
        this.accountType = accountType;
        this.status = status;
        this.pin = pin;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(status);
    }

    public boolean validatePin(String enteredPin) {
        return pin != null && pin.equals(enteredPin);
    }

    public boolean changePin(String oldPin, String newPin) {
        if (!validatePin(oldPin)) {
            return false;
        }

        if (newPin == null || !newPin.matches("\\d{4}")) {
            return false;
        }

        pin = newPin;
        return true;
    }

    @Override
    public void deposit(double amount) throws InvalidAmountException {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidAmountException(
                    "Deposit amount must be positive and finite");
        }

        balance += amount;
    }

    @Override
    public void withdraw(double amount, String enteredPin)
            throws AccountException {

        if (!validatePin(enteredPin)) {
            throw new InvalidPinException("Invalid PIN entered");
        }

        if (!isActive()) {
            throw new InactiveAccountException(
                    "Account is not active");
        }

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be positive and finite");
        }

        processDebit(amount);
    }

    public abstract void processDebit(double amount)
            throws AccountException;

    public boolean canWithdraw(double amount) {
        if (!isActive() || !Double.isFinite(amount) || amount <= 0) {
            return false;
        }

        if ("FIXED_DEPOSIT".equalsIgnoreCase(accountType)
                || "FD".equalsIgnoreCase(accountType)) {
            return false;
        }

        if (this instanceof SavingsAccount) {
            SavingsAccount savings = (SavingsAccount) this;

            return balance - amount >= savings.getMinBalance();
        }

        if (this instanceof CurrentAccount) {
            CurrentAccount current = (CurrentAccount) this;

            return amount <= balance + current.getOverdraftLimit();
        }

        return amount <= balance;
    }

    public int getTenureYears() {
        return tenureYears;
    }

    public void setTenureYears(int tenureYears) {
        if (tenureYears < 0) {
            throw new IllegalArgumentException(
                    "Tenure cannot be negative");
        }

        this.tenureYears = tenureYears;
    }

    public double getDailyTransferLimit() {
        return AccountRulesEngine.getDailyTransferLimit(
                getAccountType(), getTenureYears());
    }

    public void resetDailyTransferIfNeeded() {
        LocalDate today = LocalDate.now();

        if (!today.equals(lastTransferDate)) {
            dailyTransferTotal = 0.0;
            lastTransferDate = today;
        }
    }

    public double getDailyTransferTotal() {
        resetDailyTransferIfNeeded();
        return dailyTransferTotal;
    }

    public double getRemainingDailyTransferLimit() {
        resetDailyTransferIfNeeded();

        return Math.max(
                0.0,
                getDailyTransferLimit() - dailyTransferTotal);
    }

    public boolean canTransfer(double amount) {
        resetDailyTransferIfNeeded();

        return Double.isFinite(amount)
                && amount > 0
                && amount <= getRemainingDailyTransferLimit();
    }

    public void updateDailyTransferTotal(double amount) {
        resetDailyTransferIfNeeded();
        dailyTransferTotal += amount;
    }

    public LocalDate getLastTransferDate() {
        return lastTransferDate;
    }

    @Override
    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: Rs. " + balance);
        System.out.println("Status: " + status);
    }
}
