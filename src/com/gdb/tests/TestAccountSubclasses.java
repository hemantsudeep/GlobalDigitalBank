package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestAccountSubclasses {

    public static void main(String[] args) {

        System.out.println("=== Activity 8: Polymorphism Test ===");

        // Step 1: SavingsAccount
        // Parent reference points to SavingsAccount object
        AbstractAccount savings = new SavingsAccount(
                "SAV1001",
                "Rajesh Sharma",
                28,
                10000.0,
                "ACTIVE",
                "1234",
                1000.0,
                4.0
        );

        try {

            savings.withdraw(9500.0, "1234");

            System.out.println(
                    "[Savings] Withdraw 9500 (breaches min balance 1000): "
                            + "UNEXPECTED SUCCESS [FAIL]"
            );

        } catch (MinimumBalanceViolationException e) {

            System.out.println(
                    "[Savings] Withdraw 9500 (breaches min balance 1000): "
                            + "Caught MinimumBalanceViolationException [PASS]"
            );

        } catch (AccountException e) {

            System.out.println(
                    "[Savings] Unexpected exception: "
                            + e.getClass().getSimpleName()
                            + " [FAIL]"
            );
        }

        // Step 2: CurrentAccount
        AbstractAccount current = new CurrentAccount(
                "CUR1001",
                "Priya Patel",
                34,
                5000.0,
                "ACTIVE",
                "5678",
                25000.0
        );

        try {

            current.withdraw(10000.0, "5678");

            if (current.getBalance() == -5000.0) {

                System.out.println(
                        "[Current] Withdraw with Overdraft "
                                + "(Balance goes to -5000): "
                                + "SUCCESS [PASS]"
                );

            } else {

                System.out.println(
                        "[Current] Unexpected balance: "
                                + current.getBalance()
                                + " [FAIL]"
                );
            }

        } catch (AccountException e) {

            System.out.println(
                    "[Current] Unexpected exception: "
                            + e.getClass().getSimpleName()
                            + " [FAIL]"
            );
        }

        // Step 3: CurrentAccount exceeding overdraft
        try {

            current.withdraw(30000.0, "5678");

            System.out.println(
                    "[Current] Withdraw exceeding Overdraft "
                            + "(exceeds -25000): "
                            + "UNEXPECTED SUCCESS [FAIL]"
            );

        } catch (InsufficientBalanceException e) {

            System.out.println(
                    "[Current] Withdraw exceeding Overdraft "
                            + "(exceeds -25000): "
                            + "Caught InsufficientBalanceException [PASS]"
            );

        } catch (AccountException e) {

            System.out.println(
                    "[Current] Unexpected exception: "
                            + e.getClass().getSimpleName()
                            + " [FAIL]"
            );
        }

        // Step 4: FixedDepositAccount
        AbstractAccount fixedDeposit = new FixedDepositAccount(
                "FD1001",
                "Amit Kumar",
                45,
                50000.0,
                "ACTIVE",
                "1111",
                12,
                6.5
        );

        try {

            fixedDeposit.withdraw(1000.0, "1111");

            System.out.println(
                    "[FixedDeposit] Withdraw attempt: "
                            + "UNEXPECTED SUCCESS [FAIL]"
            );

        } catch (AccountException e) {

            System.out.println(
                    "[FixedDeposit] Withdraw attempt: "
                            + "Caught AccountException [PASS]"
            );
        }

        System.out.println(
                "All polymorphic behaviors verified!"
        );
    }
}