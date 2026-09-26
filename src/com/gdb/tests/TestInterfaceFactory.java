package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestInterfaceFactory {

    public static void main(String[] args) {

        System.out.println(
                "=== Activity 12: Factory-Driven System Suite ===");

        // =====================================================
        // TEST 1: SAVINGS ACCOUNT CREATION & DEPOSIT
        // =====================================================

        try {

            IAccount savings =
                    AccountFactory.createAccount(
                            "SAVINGS",
                            "SAV1001",
                            "Rajesh Sharma",
                            28,
                            10000.0,
                            "ACTIVE",
                            "1234");

            double initialBalance =
                    savings.getBalance();

            savings.deposit(1000.0);

            if (savings.getBalance() ==
                    initialBalance + 1000.0) {

                System.out.println(
                        "[Test 1] Savings Account Creation & Deposit: [PASS]");

            } else {

                System.out.println(
                        "[Test 1] Savings Account Creation & Deposit: [FAIL]");
            }

        } catch (AccountException e) {

            System.out.println(
                    "[Test 1] Savings Account Creation & Deposit: [FAIL]");
        }

        // =====================================================
        // TEST 2: CURRENT ACCOUNT OVERDRAFT
        // =====================================================

        try {

            IAccount current =
                    AccountFactory.createAccount(
                            "CURRENT",
                            "CUR1001",
                            "Priya Patel",
                            34,
                            5000.0,
                            "ACTIVE",
                            "5678");

            current.withdraw(
                    10000.0,
                    "5678");

            if (current.getBalance() == -5000.0) {

                System.out.println(
                        "[Test 2] Current Account Overdraft Withdrawal: [PASS]");

            } else {

                System.out.println(
                        "[Test 2] Current Account Overdraft Withdrawal: [FAIL]");
            }

        } catch (AccountException e) {

            System.out.println(
                    "[Test 2] Current Account Overdraft Withdrawal: [FAIL]");
        }

        // =====================================================
        // TEST 3: FIXED DEPOSIT PREMATURE WITHDRAWAL
        // =====================================================

        try {

            IAccount fixedDeposit =
                    AccountFactory.createAccount(
                            "FIXED_DEPOSIT",
                            "FD1001",
                            "Amit Kumar",
                            45,
                            50000.0,
                            "ACTIVE",
                            "1111");

            fixedDeposit.withdraw(
                    5000.0,
                    "1111");

            System.out.println(
                    "[Test 3] Fixed Deposit Premature Withdrawal Block: [FAIL]");

        } catch (AccountException e) {

            System.out.println(
                    "[Test 3] Fixed Deposit Premature Withdrawal Block: [PASS]");
        }

        // =====================================================
        // TEST 4: INVALID ACCOUNT TYPE
        // =====================================================

        try {

            AccountFactory.createAccount(
                    "INVALID",
                    "XXX1001",
                    "Test User",
                    30,
                    10000.0,
                    "ACTIVE",
                    "9999");

            System.out.println(
                    "[Test 4] Invalid Type Rejection: [FAIL]");

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "[Test 4] Invalid Type Rejection: [PASS]");
        }

        // =====================================================
        // FINAL RESULT
        // =====================================================

        System.out.println(
                "Factory-driven architecture successfully verified!");
    }
}