public class TestAccountEnhanced {

    private static void printAccountInfo(AccountEnhanced acc) {
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
        System.out.println(" ENHANCED ACCOUNT TEST (BOOLEAN RETURNS)");
        System.out.println("============================================================");

        // ============================================================
        // Test 1: Valid Account Creation
        // ============================================================

        System.out.println(">>> Test 1: Valid Account Creation");

        AccountEnhanced acc1 = new AccountEnhanced(
                1001,
                "John Doe",
                25,
                1000.0,
                "Savings"
        );

        printAccountInfo(acc1);
        System.out.println();


        // ============================================================
        // Test 2: Invalid Age
        // ============================================================

        System.out.println(">>> Test 2: Invalid Age (under 18)");
        System.out.println("Creating account with age 16");

        AccountEnhanced acc2 = new AccountEnhanced(
                1002,
                "Young Kid",
                16,
                500.0,
                "Savings"
        );

        System.out.println("Age auto-corrected to: " + acc2.getAge());

        printAccountInfo(acc2);
        System.out.println();


        // ============================================================
        // Test 3: Invalid Account Type
        // ============================================================

        System.out.println(">>> Test 3: Invalid Account Type");
        System.out.println("Creating account with type \"Invalid\"");

        AccountEnhanced acc3 = new AccountEnhanced(
                1003,
                "Test User",
                25,
                300.0,
                "Invalid"
        );

        System.out.println(
                "Account type defaulted to: " + acc3.getAccountType()
        );

        printAccountInfo(acc3);
        System.out.println();


        // ============================================================
        // Test 4: Minimum Balance Enforcement on Creation
        // ============================================================

        System.out.println(">>> Test 4: Minimum Balance Enforcement on Creation");
        System.out.println("Creating Savings account with ₹300 (below minimum)");

        AccountEnhanced acc4 = new AccountEnhanced(
                1004,
                "Bob Wilson",
                25,
                300.0,
                "Savings"
        );

        System.out.println(
                "Balance auto-corrected to minimum: ₹" +
                        acc4.getBalance()
        );

        printAccountInfo(acc4);
        System.out.println();


        // ============================================================
        // Test 5: Withdrawal with Minimum Balance
        // ============================================================

        System.out.println(">>> Test 5: Withdrawal with Minimum Balance");

        /*
         * Current accounts require a minimum balance of ₹1000.
         * Therefore we start with ₹1500 so that a ₹200 withdrawal
         * is valid and leaves ₹1300.
         */

        AccountEnhanced acc5 = new AccountEnhanced(
                1005,
                "Alice Brown",
                30,
                1500.0,
                "Current"
        );

        acc5.setPin(1234);

        System.out.print("Initial: ");
        printAccountInfo(acc5);

        boolean success = acc5.withdraw(200.0, 1234);

        System.out.println(
                "Withdrawing ₹200.0: " +
                        (success ? "SUCCESS" : "FAILED")
        );

        System.out.println("New balance: ₹" + acc5.getBalance());

        printAccountInfo(acc5);

        success = acc5.withdraw(900.0, 1234);

        System.out.println(
                "Withdrawing ₹900.0: " +
                        (success
                                ? "SUCCESS"
                                : "FAILED (Minimum balance violation)")
        );

        System.out.println("Current balance: ₹" + acc5.getBalance());
        System.out.println();


        // ============================================================
        // Test 6: Account Status Management
        // ============================================================

        System.out.println(">>> Test 6: Account Status Management");

        AccountEnhanced acc6 = new AccountEnhanced(
                1006,
                "Charlie Green",
                35,
                2000.0,
                "Savings"
        );

        System.out.print("Initial: ");
        printAccountInfo(acc6);

        success = acc6.closeAccount();

        System.out.println(
                "Closing account: " +
                        (success ? "SUCCESS" : "FAILED")
        );

        System.out.print("After close: ");
        printAccountInfo(acc6);

        success = acc6.deposit(500.0);

        System.out.println(
                "Depositing ₹500.0 to closed account: " +
                        (success
                                ? "SUCCESS"
                                : "FAILED (Account inactive)")
        );

        success = acc6.reopenAccount();

        System.out.println(
                "Reopening account: " +
                        (success ? "SUCCESS" : "FAILED")
        );

        System.out.print("After reopen: ");
        printAccountInfo(acc6);

        System.out.println();


        // ============================================================
        // Test 7: PIN Protection
        // ============================================================

        System.out.println(">>> Test 7: PIN Protection");

        AccountEnhanced acc7 = new AccountEnhanced(
                1007,
                "Diana Prince",
                28,
                1500.0,
                "Savings"
        );

        success = acc7.setPin(1234);

        System.out.println(
                "Setting PIN 1234: " +
                        (success ? "SUCCESS" : "FAILED")
        );

        success = acc7.verifyPin(1234);

        System.out.println(
                "Verifying correct PIN (1234): " +
                        (success ? "SUCCESS" : "FAILED")
        );

        success = acc7.verifyPin(9999);

        System.out.println(
                "Verifying incorrect PIN (9999): " +
                        (success ? "SUCCESS" : "FAILED (Incorrect PIN)")
        );

        success = acc7.withdraw(200.0, 1234);

        System.out.println(
                "Withdrawing ₹200.0 with correct PIN (1234): " +
                        (success ? "SUCCESS" : "FAILED")
        );

        System.out.println("New balance: ₹" + acc7.getBalance());


        AccountEnhanced acc8 = new AccountEnhanced(
                1008,
                "No Pin User",
                30,
                1000.0,
                "Savings"
        );

        success = acc8.withdraw(100.0, 1234);

        System.out.println(
                "Withdrawing ₹100.0 with PIN not set: " +
                        (success
                                ? "SUCCESS"
                                : "FAILED (PIN not set)")
        );

        System.out.println();


        // ============================================================
        // Test 8: All Accounts Summary
        // ============================================================

        System.out.println(">>> Test 8: All Accounts Summary");

        printAccountInfo(acc1);
        printAccountInfo(acc2);
        printAccountInfo(acc3);
        printAccountInfo(acc4);
        printAccountInfo(acc5);
        printAccountInfo(acc6);
        printAccountInfo(acc7);
        printAccountInfo(acc8);

        System.out.println();

        System.out.println("============================================================");
        System.out.println(" ENHANCED TEST COMPLETED!");
        System.out.println("============================================================");
    }
}