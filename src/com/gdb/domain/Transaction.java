
package com.gdb.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class Transaction {

    private static final AtomicLong ID_COUNTER =
            new AtomicLong(1000);

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String transactionId;
    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;
    private String accountNumber;
    private String description;

    public Transaction(
            TransactionType type,
            double amount,
            String accountNumber,
            String description) {

        if (type == null) {
            throw new IllegalArgumentException(
                    "Transaction type cannot be null");
        }

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be positive and finite");
        }

        this.transactionId =
                "TXN" + ID_COUNTER.incrementAndGet();

        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        if (type == null) {
            throw new IllegalArgumentException(
                    "Transaction type cannot be null");
        }

        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be positive and finite");
        }

        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException(
                    "Transaction timestamp cannot be null");
        }

        this.timestamp = timestamp;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction {" +
                "transactionId='" + transactionId + '\'' +
                ", type=" + type +
                ", amount=Rs. " + String.format("%.2f", amount) +
                ", accountNumber='" + accountNumber + '\'' +
                ", timestamp='" + timestamp.format(FORMATTER) + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
