import com.gdb.exceptions.*;

public class Account {

    // ===== Constants =====

    private static final double MIN_BALANCE_SAVINGS = 500.0;
    private static final double MIN_BALANCE_CURRENT = 1000.0;
    private static final int MIN_AGE = 18;
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;


    // ===== Fields =====

    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;


    // ===== Constructor =====

    public Account(int accountNumber, String name, int age,
                   double initialBalance, String accountType)
            throws IllegalArgumentException {

        if (age < MIN_AGE) {
            throw new IllegalArgumentException(
                    "Account holder must be at least 18 years old"
            );
        }

        if (!"Savings".equals(accountType)
                && !"Current".equals(accountType)) {

            throw new IllegalArgumentException(
                    "Invalid account type. Must be Savings or Current"
            );
        }

        double minimumBalance = getMinimumBalance(accountType);

        if (initialBalance < minimumBalance) {
            throw new IllegalArgumentException(
                    "Initial balance must be at least ₹" + minimumBalance
            );
        }

        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.status = "Active";
        this.pin = null;
    }


    // ===== Business Methods =====

    public void deposit(double amount)
            throws InvalidAmountException, InactiveAccountException {

        validateActive();

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero"
            );
        }

        balance += amount;
    }


    public void withdraw(double amount, int pin)
            throws InvalidAmountException,
            InsufficientBalanceException,
            MinimumBalanceViolationException,
            InactiveAccountException,
            InvalidPinException {

        validateActive();

        if (this.pin == null) {
            throw new InvalidPinException(
                    "PIN is not set"
            );
        }

        if (!verifyPin(pin)) {
            throw new InvalidPinException(
                    "Incorrect PIN"
            );
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero"
            );
        }

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        double minimumBalance = getMinimumBalance();

        if (balance - amount < minimumBalance) {
            throw new MinimumBalanceViolationException(
                    "Withdrawal would violate minimum balance of ₹"
                            + minimumBalance
            );
        }

        balance -= amount;
    }


    // ===== Account Status Management =====

    public void closeAccount() throws IllegalStateException {

        if ("Inactive".equals(status)) {
            throw new IllegalStateException(
                    "Account is already closed"
            );
        }

        status = "Inactive";
    }


    public void reopenAccount() throws IllegalStateException {

        if ("Active".equals(status)) {
            throw new IllegalStateException(
                    "Account is already active"
            );
        }

        status = "Active";
    }


    // ===== PIN Management =====

    public void setPin(int pin) throws IllegalArgumentException {

        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException(
                    "PIN must be a 4-digit number"
            );
        }

        this.pin = pin;
    }


    public boolean verifyPin(int pin) {

        if (this.pin == null) {
            return false;
        }

        return this.pin == pin;
    }


    public boolean hasPin() {

        return pin != null;
    }


    // ===== Helper Methods =====

    private double getMinimumBalance() {

        return getMinimumBalance(this.accountType);
    }


    private double getMinimumBalance(String accountType) {

        if ("Savings".equals(accountType)) {
            return MIN_BALANCE_SAVINGS;
        }

        return MIN_BALANCE_CURRENT;
    }


    private void validateActive()
            throws InactiveAccountException {

        if (!"Active".equals(status)) {
            throw new InactiveAccountException(
                    "Account is inactive"
            );
        }
    }


    // ===== Getters =====

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPin() {
        return pin;
    }
}