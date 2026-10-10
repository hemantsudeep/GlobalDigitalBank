
package com.gdb.domain;

import com.gdb.exceptions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Account {

    protected String accountNumber;
    protected String name;
    protected int age;
    protected double balance;
    protected String accountType;
    protected String status;
    protected String pin;

    // Activity 15: Daily Transfer Tracking
    protected double dailyTransferTotal = 0.0;
    protected LocalDateTime lastTransferDate = LocalDateTime.now();

    // Account tenure in years
    protected int tenureYears = 0;

    public Account(String accountNumber, String name, int age,
                   double balance, String accountType,
                   String status, String pin) {

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

    // PIN Operations

    public boolean validatePin(String enteredPin) {
        return this.pin != null && this.pin.equals(enteredPin);
    }

    public boolean changePin(String oldPin, String newPin) {

        if (!validatePin(oldPin)) {
            return false;
        }

        if (newPin == null || !newPin.matches("\\d{4}")) {
            return false;
        }

        this.pin = newPin;
        return true;
    }

    // Deposit

    public void deposit(double amount)
            throws InvalidAmountException {

        if (amount <= 0 || !Double.isFinite(amount)) {
            throw new InvalidAmountException(
                    "Deposit amount must be positive");
        }

        this.balance += amount;
    }

    // Withdrawal

    public void withdraw(double amount, String enteredPin)
            throws AccountException {

        if (!validatePin(enteredPin)) {
            throw new InvalidPinException(
                    "Invalid PIN entered");
        }

        if (!isActive()) {
            throw new InactiveAccountException(
                    "Account is not active");
        }

        if (amount <= 0 || !Double.isFinite(amount)) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be positive");
        }

        if (amount > this.balance) {
            throw new InsufficientBalanceException(
                    "Insufficient funds in account");
        }

        this.balance -= amount;
    }

    // Activity 15: Account Status

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(this.status);
    }

    // Activity 15: Check available balance

    public boolean canWithdraw(double amount) {
        return Double.isFinite(amount)
                && amount > 0
                && amount <= balance;
    }

    // Activity 15: Daily Transfer Limit

    public double getDailyTransferLimit() {
        return AccountRulesEngine.getDailyTransferLimit(
                getAccountType(), getTenureYears());
    }

    public double getRemainingDailyTransferLimit() {

        resetDailyTransferIfNeeded();

        return Math.max(
                0.0,
                getDailyTransferLimit() - dailyTransferTotal
        );
    }

    public boolean canTransfer(double amount) {

        resetDailyTransferIfNeeded();

        return Double.isFinite(amount)
                && amount > 0
                && dailyTransferTotal + amount
                <= getDailyTransferLimit();
    }

    public void updateDailyTransferTotal(double amount) {

        resetDailyTransferIfNeeded();

        dailyTransferTotal += amount;
        lastTransferDate = LocalDateTime.now();
    }

    public void resetDailyTransferIfNeeded() {

        if (!lastTransferDate.toLocalDate()
                .equals(LocalDate.now())) {

            dailyTransferTotal = 0.0;
            lastTransferDate = LocalDateTime.now();
        }
    }

    // Activity 15: Transfer Tracking Getters

    public double getDailyTransferTotal() {
        return dailyTransferTotal;
    }

    public LocalDateTime getLastTransferDate() {
        return lastTransferDate;
    }

    // Tenure Getters and Setters

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

    // Display Account Information

    public void displayAccountInfo() {

        System.out.println("Account Number: " + accountNumber);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Balance: Rs " + balance);
        System.out.println("Account Type: " + accountType);
        System.out.println("Status: " + status);
    }

    // Existing Getters

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }
}
