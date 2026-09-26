public class AccountEnhanced {

    private static final double MIN_BALANCE_SAVINGS = 500.0;
    private static final double MIN_BALANCE_CURRENT = 1000.0;
    private static final int MIN_AGE = 18;
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;

    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;

    public AccountEnhanced(int accountNumber, String name, int age,
                           double initialBalance, String accountType) {

        this.accountNumber = accountNumber;
        this.name = name;

        if (age >= MIN_AGE) {
            this.age = age;
        } else {
            this.age = MIN_AGE;
        }

        if ("Savings".equals(accountType) || "Current".equals(accountType)) {
            this.accountType = accountType;
        } else {
            this.accountType = "Savings";
        }

        double minimumBalance = getMinimumBalance();

        if (initialBalance >= minimumBalance) {
            this.balance = initialBalance;
        } else {
            this.balance = minimumBalance;
        }

        this.status = "Active";
        this.pin = null;
    }

    private double getMinimumBalance() {
        if ("Savings".equals(accountType)) {
            return MIN_BALANCE_SAVINGS;
        }

        return MIN_BALANCE_CURRENT;
    }

    private boolean isActive() {
        return "Active".equals(status);
    }

    public boolean deposit(double amount) {

        if (!isActive()) {
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        balance += amount;
        return true;
    }

    public boolean withdraw(double amount, int pin) {

        if (!isActive()) {
            return false;
        }

        if (this.pin == null) {
            return false;
        }

        if (!verifyPin(pin)) {
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        double minimumBalance = getMinimumBalance();

        if (balance - amount < minimumBalance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public boolean closeAccount() {

        if (!isActive()) {
            return false;
        }

        status = "Inactive";
        return true;
    }

    public boolean reopenAccount() {

        if (isActive()) {
            return false;
        }

        status = "Active";
        return true;
    }

    public boolean setPin(int pin) {

        if (pin >= MIN_PIN && pin <= MAX_PIN) {
            this.pin = pin;
            return true;
        }

        return false;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {

        if (age >= MIN_AGE) {
            this.age = age;
        }
    }
}