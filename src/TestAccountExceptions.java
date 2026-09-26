import exceptions.*;

public class TestAccountExceptions {

    private static void printAccountInfo(Account acc) {
        System.out.println(
                "Account #" + acc.getAccountNumber() +
                        " | " + acc.getName() +
                        " (" + acc.getAge() + " yrs)" +
                        " | " + acc.getAccountType() +
                        " | ₹" + acc.getBalance() +
                        " | " + acc.getStatus() +
                        " | PIN: " + (acc.hasPin() ? "Yes" : "No")
        );
    }

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println(" ACCOUNT EXCEPTION TEST");
        System.out.println("============================================================");

        // ============================================================
        // Test 1: Valid Account Creation
        // ============================================================

        System.out.println("\n>>> Test 1: Valid Account Creation");

        try {
            Account acc1 = new Account(
                    1001,
                    "John Doe",
                    25,
                    1000.0,
                    "Savings"
            );

            printAccountInfo(acc1);

        } catch (IllegalArgumentException e) {
            System.out.println("FAILED: " + e.getMessage());
        }


        // ============================================================
        // Test 2: Invalid Age
        // ============================================================

        System.out.println("\n>>> Test 2: Invalid Age");

        try {
            Account acc2 = new Account(
                    1002,
                    "Young Kid",
                    16,
                    500.0,
                    "Savings"
            );

            System.out.println("FAILED: Invalid age was accepted.");

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "SUCCESS: Invalid age rejected - " +
                            e.getMessage()
            );
        }


        // ============================================================
        // Test 3: Invalid Account Type
        // ============================================================

        System.out.println("\n>>> Test 3: Invalid Account Type");

        try {
            Account acc3 = new Account(
                    1003,
                    "Test User",
                    25,
                    1000.0,
                    "Invalid"
            );

            System.out.println(
                    "FAILED: Invalid account type was accepted."
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "SUCCESS: Invalid account type rejected - " +
                            e.getMessage()
            );
        }


        // ============================================================
        // Test 4: Minimum Balance Violation on Creation
        // ============================================================

        System.out.println("\n>>> Test 4: Minimum Balance Validation");

        try {
            Account acc4 = new Account(
                    1004,
                    "Bob Wilson",
                    25,
                    300.0,
                    "Savings"
            );

            System.out.println(
                    "FAILED: Account with insufficient initial balance was accepted."
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "SUCCESS: Minimum balance violation rejected - " +
                            e.getMessage()
            );
        }


        // ============================================================
        // Test 5: Deposit Exceptions
        // ============================================================

        System.out.println("\n>>> Test 5: Deposit Exceptions");

        try {
            Account acc5 = new Account(
                    1005,
                    "Alice Brown",
                    30,
                    1500.0,
                    "Savings"
            );

            acc5.deposit(500.0);

            System.out.println(
                    "Valid deposit: SUCCESS"
            );

            System.out.println(
                    "New balance: ₹" + acc5.getBalance()
            );

        } catch (InvalidAmountException | InactiveAccountException e) {
            System.out.println(
                    "FAILED: " + e.getMessage()
            );
        }


        // Invalid deposit amount

        try {
            Account acc6 = new Account(
                    1006,
                    "Invalid Deposit",
                    30,
                    1000.0,
                    "Savings"
            );

            acc6.deposit(-100.0);

            System.out.println(
                    "FAILED: Negative deposit was accepted."
            );

        } catch (InvalidAmountException e) {
            System.out.println(
                    "SUCCESS: Invalid deposit rejected - " +
                            e.getMessage()
            );

        } catch (InactiveAccountException e) {
            System.out.println(
                    "FAILED: Account inactive - " +
                            e.getMessage()
            );
        }


        // ============================================================
        // Test 6: PIN Protection
        // ============================================================

        System.out.println("\n>>> Test 6: PIN Protection");

        try {
            Account acc7 = new Account(
                    1007,
                    "Diana Prince",
                    28,
                    1500.0,
                    "Savings"
            );

            acc7.setPin(1234);

            System.out.println(
                    "PIN set successfully."
            );

            System.out.println(
                    "Correct PIN verification: " +
                            (acc7.verifyPin(1234) ? "SUCCESS" : "FAILED")
            );

            System.out.println(
                    "Incorrect PIN verification: " +
                            (acc7.verifyPin(9999) ? "FAILED" : "SUCCESS")
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "FAILED: " + e.getMessage()
            );
        }


        // ============================================================
        // Test 7: Successful Withdrawal
        // ============================================================

        System.out.println("\n>>> Test 7: Successful Withdrawal");

        try {
            Account acc8 = new Account(
                    1008,
                    "Withdrawal User",
                    30,
                    2000.0,
                    "Savings"
            );

            acc8.setPin(1234);

            acc8.withdraw(500.0, 1234);

            System.out.println(
                    "Withdrawal: SUCCESS"
            );

            System.out.println(
                    "New balance: ₹" + acc8.getBalance()
            );

        } catch (InvalidAmountException |
                 InsufficientBalanceException |
                 MinimumBalanceViolationException |
                 InactiveAccountException |
                 InvalidPinException e) {

            System.out.println(
                    "FAILED: " + e.getMessage()
            );
        }


        // ============================================================
        // Test 8: Incorrect PIN
        // ============================================================

        System.out.println("\n>>> Test 8: Incorrect PIN");

        try {
            Account acc9 = new Account(
                    1009,
                    "PIN Test",
                    30,
                    1500.0,
                    "Savings"
            );

            acc9.setPin(1234);

            acc9.withdraw(100.0, 9999);

            System.out.println(
                    "FAILED: Incorrect PIN was accepted."
            );

        } catch (InvalidPinException e) {

            System.out.println(
                    "SUCCESS: Incorrect PIN rejected - " +
                            e.getMessage()
            );

        } catch (InvalidAmountException |
                 InsufficientBalanceException |
                 MinimumBalanceViolationException |
                 InactiveAccountException e) {

            System.out.println(
                    "FAILED: " + e.getMessage()
            );
        }


        // ============================================================
        // Test 9: Insufficient Balance
        // ============================================================

        System.out.println("\n>>> Test 9: Insufficient Balance");

        try {
            Account acc10 = new Account(
                    1010,
                    "Balance Test",
                    30,
                    1000.0,
                    "Savings"
            );

            acc10.setPin(1234);

            acc10.withdraw(2000.0, 1234);

            System.out.println(
                    "FAILED: Insufficient balance was accepted."
            );

        } catch (InsufficientBalanceException e) {

            System.out.println(
                    "SUCCESS: Insufficient balance rejected - " +
                            e.getMessage()
            );

        } catch (InvalidAmountException |
                 MinimumBalanceViolationException |
                 InactiveAccountException |
                 InvalidPinException e) {

            System.out.println(
                    "FAILED: " + e.getMessage()
            );
        }


        // ============================================================
        // Test 10: Minimum Balance Violation
        // ============================================================

        System.out.println("\n>>> Test 10: Minimum Balance Violation");

        try {
            Account acc11 = new Account(
                    1011,
                    "Minimum Balance Test",
                    30,
                    1000.0,
                    "Savings"
            );

            acc11.setPin(1234);

            acc11.withdraw(600.0, 1234);

            System.out.println(
                    "FAILED: Minimum balance violation was accepted."
            );

        } catch (MinimumBalanceViolationException e) {

            System.out.println(
                    "SUCCESS: Minimum balance violation rejected - " +
                            e.getMessage()
            );

        } catch (InvalidAmountException |
                 InsufficientBalanceException |
                 InactiveAccountException |
                 InvalidPinException e) {

            System.out.println(
                    "FAILED: " + e.getMessage()
            );
        }


        // ============================================================
        // Test 11: Inactive Account
        // ============================================================

        System.out.println("\n>>> Test 11: Inactive Account");

        try {
            Account acc12 = new Account(
                    1012,
                    "Inactive Test",
                    30,
                    1000.0,
                    "Savings"
            );

            acc12.closeAccount();

            try {
                acc12.deposit(500.0);

                System.out.println(
                        "FAILED: Deposit accepted on inactive account."
                );

            } catch (InactiveAccountException e) {

                System.out.println(
                        "SUCCESS: Deposit rejected - " +
                                e.getMessage()
                );
            }

        } catch (IllegalStateException e) {

            System.out.println(
                    "FAILED: " + e.getMessage()
            );
        } catch (InvalidAmountException e) {

            System.out.println(
                    "FAILED: " + e.getMessage()
            );
        }


        // ============================================================
        // Test 12: Close and Reopen Account
        // ============================================================

        System.out.println("\n>>> Test 12: Close and Reopen Account");

        try {
            Account acc13 = new Account(
                    1013,
                    "Status Test",
                    30,
                    1000.0,
                    "Savings"
            );

            System.out.println(
                    "Initial status: " + acc13.getStatus()
            );

            acc13.closeAccount();

            System.out.println(
                    "After close: " + acc13.getStatus()
            );

            acc13.reopenAccount();

            System.out.println(
                    "After reopen: " + acc13.getStatus()
            );

        } catch (IllegalStateException e) {

            System.out.println(
                    "FAILED: " + e.getMessage()
            );
        }


        System.out.println("\n============================================================");
        System.out.println(" EXCEPTION TEST COMPLETED!");
        System.out.println("============================================================");
    }
}