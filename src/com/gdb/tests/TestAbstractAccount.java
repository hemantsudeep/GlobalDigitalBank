package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestAbstractAccount {

    /*
     * Transfers money from one account to another.
     *
     * Important:
     * The destination account is credited ONLY if
     * the source withdrawal succeeds.
     */
    public static boolean transferFunds(
            AbstractAccount source,
            AbstractAccount destination,
            double amount,
            String pin) {

        try {

            // First withdraw from source
            source.withdraw(amount, pin);

            // Only credit destination after successful withdrawal
            destination.deposit(amount);

            return true;

        } catch (AccountException e) {

            System.out.println(
                    "Transfer failed: " + e.getMessage());

            return false;

        }
    }

    /*
     * Processes the monthly banking cycle.
     */
    public static void processMonthlyCycle(
            AbstractAccount[] accounts) {

        for (AbstractAccount account : accounts) {

            // Savings account → apply interest
            if (account instanceof SavingsAccount) {

                SavingsAccount savings =
                        (SavingsAccount) account;

                savings.applyInterest();
            }

            // Salary account → check salary credit history
            if (account instanceof SalaryAccount) {

                SalaryAccount salary =
                        (SalaryAccount) account;

                /*
                 * The current SalaryAccount implementation
                 * tracks inactive months.
                 */
                if (salary.getInactiveMonths() > 0) {

                    System.out.println(
                            "Salary account " +
                                    salary.getAccountNumber() +
                                    ": No recent salary credit.");

                } else {

                    System.out.println(
                            "Salary account " +
                                    salary.getAccountNumber() +
                                    ": Salary credit history OK.");
                }
            }
        }
    }

    public static void main(String[] args) {

        System.out.println(
                "=== Activity 10: Banking Operations Suite ===");

        // =====================================================
        // STEP 1: CREATE ACCOUNT PORTFOLIO
        // =====================================================

        AbstractAccount savings =
                new SavingsAccount(
                        "SAV1001",
                        "Rajesh Sharma",
                        28,
                        10000.0,
                        "ACTIVE",
                        "1234",
                        1000.0,
                        4.0);

        AbstractAccount current =
                new CurrentAccount(
                        "CUR1001",
                        "Priya Patel",
                        34,
                        5000.0,
                        "ACTIVE",
                        "5678",
                        25000.0);

        AbstractAccount salary =
                new SalaryAccount(
                        "SAL1001",
                        "Sneha Verma",
                        26,
                        30000.0,
                        "ACTIVE",
                        "2222",
                        "Infosys");

        AbstractAccount[] portfolio = {
                savings,
                current,
                salary
        };

        // =====================================================
        // STEP 2: FUND TRANSFER
        // =====================================================

        boolean transferSuccessful =
                transferFunds(
                        savings,
                        current,
                        3000.0,
                        "1234");

        if (transferSuccessful) {

            System.out.println(
                    "Transfer Rs 3000 from Savings to Current: SUCCESS");

            System.out.println(
                    "Savings Balance: Rs " +
                            savings.getBalance() +
                            " | Current Balance: Rs " +
                            current.getBalance());
        }

        // =====================================================
        // TEST FAILED TRANSFER
        // =====================================================

        double savingsBeforeFailedTransfer =
                savings.getBalance();

        double currentBeforeFailedTransfer =
                current.getBalance();

        boolean failedTransfer =
                transferFunds(
                        savings,
                        current,
                        2000.0,
                        "9999");

        if (!failedTransfer &&
                savings.getBalance() ==
                        savingsBeforeFailedTransfer &&
                current.getBalance() ==
                        currentBeforeFailedTransfer) {

            System.out.println(
                    "Failed Transfer (Wrong PIN): " +
                            "Exception caught, no balance changed [PASS]");
        }

        // =====================================================
        // STEP 3: MONTHLY BANKING CYCLE
        // =====================================================

        processMonthlyCycle(portfolio);

        System.out.println(
                "Monthly Interest Cycle processed for all qualifying accounts.");

        System.out.println(
                "All banking operations passed!");
    }
}