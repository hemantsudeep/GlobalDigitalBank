
package com.gdb.tests;

import com.gdb.domain.AbstractAccount;
import com.gdb.domain.AccountFactory;
import com.gdb.domain.Transaction;
import com.gdb.domain.TransactionType;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InvalidAmountException;
import com.gdb.service.TransferService;

public class TestTransactionModel {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        System.out.println("=== Activity 16: Transaction Model Tests ===\n");

        testDepositTransaction();
        testWithdrawalTransaction();
        testTransferTransaction();
        testUniqueTransactionIds();
        testInvalidDeposit();
        testInvalidWithdrawal();
        testBackwardCompatibility();

        System.out.println("\n========================================");
        System.out.println("Tests passed: " + passed);
        System.out.println("Tests failed: " + failed);
        System.out.println("========================================");
    }

    private static void testDepositTransaction() {

        AbstractAccount account = AccountFactory.createAccount(
                "SAVINGS", "TX1001", "Test Customer",
                25, 10000.0, "ACTIVE", "1234");

        try {
            Transaction transaction =
                    account.depositWithTransaction(2000.0);

            boolean result =
                    transaction.getType() == TransactionType.DEPOSIT
                            && transaction.getAmount() == 2000.0
                            && "TX1001".equals(transaction.getAccountNumber())
                            && account.getBalance() == 12000.0
                            && transaction.getTransactionId() != null
                            && transaction.getTimestamp() != null;

            printResult("Deposit Transaction", result);

            if (!result) {
                System.out.println(transaction);
            }

        } catch (InvalidAmountException e) {
            printResult("Deposit Transaction", false);
            System.out.println("Reason: " + e.getMessage());
        }
    }

    private static void testWithdrawalTransaction() {

        AbstractAccount account = AccountFactory.createAccount(
                "SAVINGS", "TX1002", "Test Customer",
                25, 20000.0, "ACTIVE", "1234");

        try {
            Transaction transaction =
                    account.withdrawWithTransaction(1000.0, "1234");

            boolean result =
                    transaction.getType() == TransactionType.WITHDRAW
                            && transaction.getAmount() == 1000.0
                            && "TX1002".equals(transaction.getAccountNumber())
                            && account.getBalance() == 19000.0;

            printResult("Withdrawal Transaction", result);

            if (!result) {
                System.out.println(transaction);
            }

        } catch (AccountException e) {
            printResult("Withdrawal Transaction", false);
            System.out.println("Reason: " + e.getMessage());
        }
    }

    private static void testTransferTransaction() {

        AbstractAccount sender = AccountFactory.createAccount(
                "SAVINGS", "TX1003", "Sender",
                30, 50000.0, "ACTIVE", "1234");

        AbstractAccount receiver = AccountFactory.createAccount(
                "SAVINGS", "TX1004", "Receiver",
                28, 10000.0, "ACTIVE", "5678");

        TransferService service = new TransferService();

        try {
            Transaction transaction =
                    service.transferWithTransaction(
                            sender, receiver, 2000.0, "1234");

            boolean result =
                    transaction.getType() == TransactionType.TRANSFER
                            && transaction.getAmount() == 2000.0
                            && "TX1003".equals(transaction.getAccountNumber())
                            && sender.getBalance() == 48000.0
                            && receiver.getBalance() == 12000.0
                            && sender.getDailyTransferTotal() == 2000.0
                            && transaction.getDescription().contains("TX1004");

            printResult("Transfer Transaction", result);

            if (result) {
                System.out.println("Transaction details:");
                System.out.println(transaction);
            }

        } catch (AccountException e) {
            printResult("Transfer Transaction", false);
            System.out.println("Reason: " + e.getMessage());
        }
    }

    private static void testUniqueTransactionIds() {

        AbstractAccount account = AccountFactory.createAccount(
                "SAVINGS", "TX1005", "ID Test Customer",
                25, 10000.0, "ACTIVE", "1234");

        try {
            Transaction first =
                    account.depositWithTransaction(100.0);

            Transaction second =
                    account.depositWithTransaction(200.0);

            boolean result =
                    first.getTransactionId() != null
                            && second.getTransactionId() != null
                            && !first.getTransactionId().equals(
                            second.getTransactionId());

            printResult("Unique Transaction IDs", result);

        } catch (InvalidAmountException e) {
            printResult("Unique Transaction IDs", false);
            System.out.println("Reason: " + e.getMessage());
        }
    }

    private static void testInvalidDeposit() {

        AbstractAccount account = AccountFactory.createAccount(
                "SAVINGS", "TX1006", "Invalid Deposit Test",
                25, 10000.0, "ACTIVE", "1234");

        double initialBalance = account.getBalance();

        try {
            account.depositWithTransaction(-500.0);
            printResult("Invalid Deposit Rejection", false);

        } catch (InvalidAmountException e) {
            boolean result = account.getBalance() == initialBalance;
            printResult("Invalid Deposit Rejection", result);
        }
    }

    private static void testInvalidWithdrawal() {

        AbstractAccount account = AccountFactory.createAccount(
                "SAVINGS", "TX1007", "Invalid Withdrawal Test",
                25, 10000.0, "ACTIVE", "1234");

        double initialBalance = account.getBalance();

        try {
            account.withdrawWithTransaction(1000.0, "9999");
            printResult("Invalid Withdrawal PIN Rejection", false);

        } catch (AccountException e) {
            boolean result = account.getBalance() == initialBalance;
            printResult("Invalid Withdrawal PIN Rejection", result);
        }
    }

    private static void testBackwardCompatibility() {

        AbstractAccount account = AccountFactory.createAccount(
                "SAVINGS", "TX1008", "Compatibility Test",
                25, 10000.0, "ACTIVE", "1234");

        try {
            // Original void-returning methods must still work.
            account.deposit(500.0);
            account.withdraw(200.0, "1234");

            boolean result = account.getBalance() == 10300.0;

            printResult("Backward Compatibility", result);

        } catch (Exception e) {
            printResult("Backward Compatibility", false);
            System.out.println("Reason: " + e.getMessage());
        }
    }

    private static void printResult(String testName, boolean result) {

        if (result) {
            passed++;
            System.out.println("[PASS] " + testName);
        } else {
            failed++;
            System.out.println("[FAIL] " + testName);
        }
    }
}
