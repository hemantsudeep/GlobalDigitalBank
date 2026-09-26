package com.gdb.domain;

public class AccountFactory {

    public static AbstractAccount createAccount(
            String accountType,
            String accountNumber,
            String name,
            int age,
            double balance,
            String status,
            String pin) {

        switch (accountType.toUpperCase()) {

            case "SAVINGS":
                return new SavingsAccount(
                        accountNumber,
                        name,
                        age,
                        balance,
                        status,
                        pin
                );

            case "CURRENT":
                return new CurrentAccount(
                        accountNumber,
                        name,
                        age,
                        balance,
                        status,
                        pin,
                        25000.0
                );

            case "FIXED_DEPOSIT":
            case "FD":
                return new FixedDepositAccount(
                        accountNumber,
                        name,
                        age,
                        balance,
                        status,
                        pin,
                        12,
                        6.5
                );

            case "SALARY":
                return new SalaryAccount(
                        accountNumber,
                        name,
                        age,
                        balance,
                        status,
                        pin,
                        "Infosys"
                );

            default:
                throw new IllegalArgumentException(
                        "Unknown account type: " + accountType
                );
        }
    }
}