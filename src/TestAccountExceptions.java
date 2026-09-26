import com.gdb.exceptions.*;

public class TestAccountExceptions {

    private static void printAccountInfo(Account acc) {
        String pinStatus = acc.hasPin() ? "Yes" : "No";

        System.out.println(
                "Account #" + acc.getAccountNumber() +
                        " | " + acc.getName() +
                        " (" + acc.getAge() + " yrs)" +
                        " | " + acc.getAccountType() +
                        " | ₹" + acc.getBalance() +
                        " | " + acc.getStatus() +
                        " | PIN: " + pinStatus
        );
    }

    private static void printException(Exception e) {
        System.out.println(
                "EXCEPTION: " +
                        e.getClass().getSimpleName() +
                        " - " +
                        e.getMessage()
        );
    }

    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("  ACCOUNT TEST WITH EXCEPTIONS");
        System.out.println("=".repeat(60));
        System.out.println();


        // =====================================================
        // TEST 1: VALID ACCOUNT CREATION
        // =====================================================

        System.out.println(">>> Test 1: Valid Account Creation");

        try {
            Account acc1 =
                    new Account(1001, "John Doe", 25,
                            1000.0, "Savings");

            System.out.print("SUCCESS: ");
            printAccountInfo(acc1);

        } catch (IllegalArgumentException e) {
            printException(e);
        }

        System.out.println();


        // =====================================================
        // TEST 2: INVALID AGE
        // =====================================================

        System.out.println(">>> Test 2: Invalid Age (under 18)");

        try {
            Account acc2 =
                    new Account(1002, "Young Kid", 16,
                            500.0, "Savings");

            System.out.print("SUCCESS: ");
            printAccountInfo(acc2);

        } catch (IllegalArgumentException e) {
            printException(e);
        }

        System.out.println();


        // =====================================================
        // TEST 3: INVALID ACCOUNT TYPE
        // =====================================================

        System.out.println(">>> Test 3: Invalid Account Type");

        try {
            Account acc3 =
                    new Account(1003, "Test User", 25,
                            1000.0, "Invalid");

            System.out.print("SUCCESS: ");
            printAccountInfo(acc3);

        } catch (IllegalArgumentException e) {
            printException(e);
        }

        System.out.println();


        // =====================================================
        // TEST 4: MINIMUM BALANCE
        // =====================================================

        System.out.println(">>> Test 4: Minimum Balance on Creation");

        System.out.println(
                "Creating Savings account with ₹300"
        );

        try {
            Account acc4 =
                    new Account(1004, "Bob Wilson", 25,
                            300.0, "Savings");

            System.out.print("SUCCESS: ");
            printAccountInfo(acc4);

        } catch (IllegalArgumentException e) {
            printException(e);
        }

        System.out.println();


        // =====================================================
        // TEST 5: VALID DEPOSIT AND WITHDRAWAL
        // =====================================================

        System.out.println(">>> Test 5: Valid Deposit and Withdrawal");

        try {

            Account acc5 =
                    new Account(
                            1005,
                            "Alice Brown",
                            30,
                            1000.0,
                            "Current"
                    );

            System.out.print("Account: ");
            printAccountInfo(acc5);

            acc5.setPin(1234);

            System.out.println(
                    "Setting PIN 1234: SUCCESS"
            );

            acc5.deposit(500.0);

            System.out.println(
                    "Depositing ₹500.0: SUCCESS"
            );

            System.out.println(
                    "Balance after deposit: ₹" +
                            acc5.getBalance()
            );

            acc5.withdraw(200.0, 1234);

            System.out.println(
                    "Withdrawing ₹200.0: SUCCESS"
            );

            System.out.println(
                    "Balance after withdrawal: ₹" +
                            acc5.getBalance()
            );

            System.out.print("Final: ");
            printAccountInfo(acc5);

        } catch (Exception e) {
            printException(e);
        }

        System.out.println();


        // =====================================================
        // TEST 6: INVALID DEPOSIT
        // =====================================================

        System.out.println(
                ">>> Test 6: Invalid Deposit (Negative Amount)"
        );

        try {

            Account acc6 =
                    new Account(
                            1006,
                            "Alice Brown",
                            30,
                            1000.0,
                            "Current"
                    );

            acc6.setPin(1234);

            System.out.println(
                    "Attempting to deposit ₹-100.0"
            );

            acc6.deposit(-100.0);

            System.out.println(
                    "ERROR: Deposit should have failed."
            );

        } catch (InvalidAmountException e) {

            printException(e);

        } catch (Exception e) {

            printException(e);
        }

        System.out.println();


        // =====================================================
        // TEST 7: INSUFFICIENT BALANCE
        // =====================================================

        System.out.println(
                ">>> Test 7: Insufficient Balance"
        );

        try {

            Account acc7 =
                    new Account(
                            1007,
                            "Charlie Green",
                            35,
                            500.0,
                            "Savings"
                    );

            acc7.setPin(1234);

            System.out.print("Account: ");
            printAccountInfo(acc7);

            System.out.println(
                    "Attempting to withdraw ₹1000.0"
            );

            acc7.withdraw(1000.0, 1234);

            System.out.println(
                    "ERROR: Withdrawal should have failed."
            );

        } catch (InsufficientBalanceException e) {

            printException(e);

        } catch (Exception e) {

            printException(e);
        }

        System.out.println();


        // =====================================================
        // TEST 8: MINIMUM BALANCE VIOLATION
        // =====================================================

        System.out.println(
                ">>> Test 8: Minimum Balance Violation"
        );

        try {

            Account acc8 =
                    new Account(
                            1008,
                            "Diana Prince",
                            28,
                            1000.0,
                            "Savings"
                    );

            acc8.setPin(1234);

            System.out.print("Account: ");
            printAccountInfo(acc8);

            System.out.println(
                    "Attempting to withdraw ₹600.0"
            );

            acc8.withdraw(600.0, 1234);

            System.out.println(
                    "ERROR: Withdrawal should have failed."
            );

        } catch (MinimumBalanceViolationException e) {

            printException(e);

        } catch (Exception e) {

            printException(e);
        }

        System.out.println();


        // =====================================================
        // TEST 9: INACTIVE ACCOUNT
        // =====================================================

        System.out.println(
                ">>> Test 9: Inactive Account Operations"
        );

        Account acc9 = null;

        try {

            acc9 =
                    new Account(
                            1009,
                            "Eve Wilson",
                            32,
                            2000.0,
                            "Current"
                    );

            System.out.print("Account: ");
            printAccountInfo(acc9);

            acc9.closeAccount();

            System.out.println(
                    "Closing account: SUCCESS"
            );

            System.out.println(
                    "Attempting to deposit ₹100.0 on closed account"
            );

            acc9.deposit(100.0);

        } catch (InactiveAccountException e) {

            printException(e);

            try {

                acc9.reopenAccount();

                System.out.println(
                        "Reopening account: SUCCESS"
                );

                acc9.deposit(100.0);

                System.out.println(
                        "Depositing ₹100.0 after reopen: SUCCESS"
                );

                System.out.println(
                        "Balance after deposit: ₹" +
                                acc9.getBalance()
                );

            } catch (Exception ex) {

                printException(ex);
            }

        } catch (Exception e) {

            printException(e);
        }

        System.out.println();


        // =====================================================
        // TEST 10: PIN VERIFICATION
        // =====================================================

        System.out.println(
                ">>> Test 10: PIN Verification"
        );

        try {

            Account acc10 =
                    new Account(
                            1010,
                            "Frank Miller",
                            40,
                            1500.0,
                            "Savings"
                    );

            System.out.print("Account: ");
            printAccountInfo(acc10);

            acc10.setPin(1234);

            System.out.println(
                    "Setting PIN 1234: SUCCESS"
            );

            acc10.withdraw(200.0, 1234);

            System.out.println(
                    "Withdrawing ₹200.0 with correct PIN: SUCCESS"
            );

            System.out.println(
                    "Balance: ₹" +
                            acc10.getBalance()
            );

            System.out.println(
                    "Attempting to withdraw ₹100.0 " +
                            "with incorrect PIN (9999)"
            );

            acc10.withdraw(100.0, 9999);

        } catch (InvalidPinException e) {

            printException(e);

        } catch (Exception e) {

            printException(e);
        }


        // =====================================================
        // TEST 10B: PIN NOT SET
        // =====================================================

        System.out.println();

        System.out.println(
                ">>> Test 10B: PIN Not Set"
        );

        try {

            Account acc11 =
                    new Account(
                            1011,
                            "Grace Lee",
                            35,
                            1000.0,
                            "Savings"
                    );

            System.out.println(
                    "Attempting to withdraw ₹100.0 without PIN set"
            );

            acc11.withdraw(100.0, 1234);

        } catch (InvalidPinException e) {

            printException(e);

        } catch (Exception e) {

            printException(e);
        }

        System.out.println();


        // =====================================================
        // TEST 11: ALL ACCOUNTS SUMMARY
        // =====================================================

        System.out.println(
                ">>> Test 11: All Accounts Summary"
        );

        try {

            Account[] accounts = {

                    new Account(
                            1001,
                            "John Doe",
                            25,
                            1000.0,
                            "Savings"
                    ),

                    new Account(
                            1005,
                            "Alice Brown",
                            30,
                            1300.0,
                            "Current"
                    ),

                    new Account(
                            1006,
                            "Charlie Green",
                            35,
                            500.0,
                            "Savings"
                    ),

                    new Account(
                            1007,
                            "Diana Prince",
                            28,
                            1000.0,
                            "Savings"
                    ),

                    new Account(
                            1008,
                            "Eve Wilson",
                            32,
                            2100.0,
                            "Current"
                    ),

                    new Account(
                            1009,
                            "Frank Miller",
                            40,
                            1300.0,
                            "Savings"
                    )
            };

            accounts[1].setPin(1234);
            accounts[2].setPin(1234);
            accounts[3].setPin(1234);
            accounts[5].setPin(1234);

            for (Account acc : accounts) {
                printAccountInfo(acc);
            }

        } catch (Exception e) {

            printException(e);
        }

        System.out.println();

        System.out.println("=".repeat(60));
        System.out.println("  TEST COMPLETED!");
        System.out.println("=".repeat(60));
    }
}