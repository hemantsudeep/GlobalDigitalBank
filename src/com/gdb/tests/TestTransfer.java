
package com.gdb.tests;

import com.gdb.domain.AbstractAccount;
import com.gdb.domain.AccountFactory;
import com.gdb.exceptions.AccountException;
import com.gdb.service.TransferService;

public class TestTransfer {

    public static void main(String[] args) {

        TransferService transferService = new TransferService();

        System.out.println("=== Activity 15: Funds Transfer Tests ===");

        // TEST 1: Successful transfer
        AbstractAccount sender = AccountFactory.createAccount(
                "SAVINGS", "SAV1001", "Rajesh Sharma",
                28, 100000.0, "ACTIVE", "1234");

        AbstractAccount receiver = AccountFactory.createAccount(
                "SAVINGS", "SAV1002", "Priya Sharma",
                25, 20000.0, "ACTIVE", "5678");

        try {
            transferService.transfer(sender, receiver, 5000.0, "1234");

            boolean passed =
                    sender.getBalance() == 95000.0
                            && receiver.getBalance() == 25000.0
                            && sender.getDailyTransferTotal() == 5000.0;

            printResult("Successful Transfer", passed);

        } catch (AccountException e) {
            printResult("Successful Transfer", false);
            System.out.println("Reason: " + e.getMessage());
        }

        // TEST 2: Invalid PIN
        double senderBalanceBefore = sender.getBalance();

        try {
            transferService.transfer(sender, receiver, 1000.0, "9999");
            printResult("Invalid PIN Rejection", false);

        } catch (AccountException e) {
            boolean passed =
                    "Incorrect PIN".equals(e.getMessage())
                            && sender.getBalance() == senderBalanceBefore;

            printResult("Invalid PIN Rejection", passed);
        }

        // TEST 3: Daily transfer limit
        AbstractAccount limitSender = AccountFactory.createAccount(
                "SAVINGS", "SAV1003", "Test Sender",
                30, 100000.0, "ACTIVE", "1111");

        AbstractAccount limitReceiver = AccountFactory.createAccount(
                "SAVINGS", "SAV1004", "Test Receiver",
                30, 10000.0, "ACTIVE", "2222");

        try {
            transferService.transfer(
                    limitSender, limitReceiver, 30000.0, "1111");

            transferService.transfer(
                    limitSender, limitReceiver, 25000.0, "1111");

            printResult("Daily Transfer Limit Enforcement", false);

        } catch (AccountException e) {
            boolean passed =
                    e.getMessage().contains("Daily transfer limit exceeded")
                            && limitSender.getBalance() == 70000.0
                            && limitReceiver.getBalance() == 40000.0
                            && limitSender.getDailyTransferTotal() == 30000.0;

            printResult("Daily Transfer Limit Enforcement", passed);

            if (!passed) {
                System.out.println("Reason: " + e.getMessage());
            }
        }

        // TEST 4: Inactive destination account
        AbstractAccount inactiveReceiver = AccountFactory.createAccount(
                "SAVINGS", "SAV1005", "Inactive Receiver",
                25, 10000.0, "INACTIVE", "3333");

        double balanceBeforeInactiveTest = sender.getBalance();

        try {
            transferService.transfer(
                    sender, inactiveReceiver, 1000.0, "1234");

            printResult("Inactive Account Rejection", false);

        } catch (AccountException e) {
            boolean passed =
                    e.getMessage().contains("Both accounts must be active")
                            && sender.getBalance() == balanceBeforeInactiveTest;

            printResult("Inactive Account Rejection", passed);
        }

        // TEST 5: Fixed deposit cannot transfer funds out
        AbstractAccount fixedDeposit = AccountFactory.createAccount(
                "FIXED_DEPOSIT", "FD1001", "FD Customer",
                45, 50000.0, "ACTIVE", "4444");

        double fdBalanceBefore = fixedDeposit.getBalance();

        try {
            transferService.transfer(
                    fixedDeposit, receiver, 1000.0, "4444");

            printResult("Fixed Deposit Transfer Restriction", false);

        } catch (AccountException e) {
            boolean passed =
                    fixedDeposit.getBalance() == fdBalanceBefore;

            printResult("Fixed Deposit Transfer Restriction", passed);
        }

        System.out.println("\n=== Activity 15 Tests Completed ===");
    }

    private static void printResult(String testName, boolean passed) {
        System.out.println(
                "[Test] " + testName + ": "
                        + (passed ? "[PASS]" : "[FAIL]"));
    }
}

