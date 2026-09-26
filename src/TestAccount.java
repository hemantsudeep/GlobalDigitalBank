public class TestAccount {

    private static void printAccountInfo(Account acc) {
        System.out.println(
                "Account #" + acc.getAccountNumber() +
                        " | " + acc.getName() +
                        " (" + acc.getAge() + " yrs)" +
                        " | " + acc.getAccountType() +
                        " | ₹" + acc.getBalance() +
                        " | " + acc.getStatus()
        );
    }

    public static void main(String[] args) {

        System.out.println("=".repeat(50));
        System.out.println("  GLOBAL DIGITAL BANK - ACCOUNT TEST");
        System.out.println("=".repeat(50));
        System.out.println();

        // 1. Creating Account
        System.out.println(">>> 1. Creating Account");

        Account acc1 = new Account(
                1001,
                "John Doe",
                25,
                1000.0,
                "Savings"
        );

        System.out.println("Account created!");
        printAccountInfo(acc1);
        System.out.println();

        // 2. Deposit Money
        System.out.println(">>> 2. Deposit Money");

        boolean success = acc1.deposit(500.0);

        System.out.println(
                "Depositing ₹500.0: " +
                        (success ? "SUCCESS" : "FAILED")
        );

        System.out.println("New balance: ₹" + acc1.getBalance());
        System.out.println();

        // Invalid deposit
        success = acc1.deposit(-100.0);

        System.out.println(
                "Depositing ₹-100.0: " +
                        (success ? "SUCCESS" : "FAILED (Invalid amount)")
        );

        System.out.println();

        // 3. Withdraw Money
        System.out.println(">>> 3. Withdraw Money");

        success = acc1.withdraw(200.0);

        System.out.println(
                "Withdrawing ₹200.0: " +
                        (success ? "SUCCESS" : "FAILED")
        );

        System.out.println("New balance: ₹" + acc1.getBalance());
        System.out.println();

        // Insufficient balance
        success = acc1.withdraw(2000.0);

        System.out.println(
                "Withdrawing ₹2000.0: " +
                        (success ? "SUCCESS" : "FAILED (Insufficient balance)")
        );

        System.out.println("Current balance: ₹" + acc1.getBalance());
        System.out.println();

        // 4. Creating Another Account
        System.out.println(">>> 4. Creating Another Account");

        Account acc2 = new Account(
                1002,
                "Jane Smith",
                30,
                2000.0,
                "Current"
        );

        printAccountInfo(acc2);
        System.out.println();

        // 5. All Accounts
        System.out.println(">>> 5. All Accounts");

        printAccountInfo(acc1);
        System.out.println();

        printAccountInfo(acc2);
        System.out.println();

        System.out.println("=".repeat(50));
        System.out.println("  TEST COMPLETED!");
        System.out.println("=".repeat(50));
    }
}