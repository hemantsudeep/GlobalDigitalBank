package com.gdb.domain;

import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InvalidAmountException;

public interface IAccount {

    String getAccountNumber();

    String getName();

    double getBalance();

    String getAccountType();

    String getStatus();

    void deposit(double amount)
            throws InvalidAmountException;

    void withdraw(double amount, String pin)
            throws AccountException;

    void displayAccountInfo();
}