
package com.gdb.domain;

public class AccountRulesEngine {

    private static final String RULES_PATH =
            "src/main/resources/config/rules/";

    private static final AccountRulesPropertiesLoader savingsRules =
            new AccountRulesPropertiesLoader(
                    RULES_PATH + "savings.properties"
            );

    private static AccountRulesPropertiesLoader currentRules;
    private static AccountRulesPropertiesLoader fdRules;
    private static AccountRulesPropertiesLoader salaryRules;

    // Savings Account Rules

    public static double getSavingsMinBalance(int tenureYears) {

        String category;

        if (tenureYears >= 5) {
            category = "privilege";
        } else if (tenureYears >= 3) {
            category = "premium";
        } else if (tenureYears >= 1) {
            category = "standard";
        } else {
            category = "new";
        }

        return savingsRules.getDouble(
                "savings." + category + ".minBalance",
                10000.0
        );
    }

    public static double getSavingsInterestRate(int tenureYears) {

        String category;

        if (tenureYears >= 5) {
            category = "privilege";
        } else if (tenureYears >= 3) {
            category = "premium";
        } else if (tenureYears >= 1) {
            category = "standard";
        } else {
            category = "new";
        }

        return savingsRules.getDouble(
                "savings." + category + ".interestRate",
                2.70
        );
    }

    // Current Account Rules

    public static double getCurrentOverdraftLimit(
            double monthlyTurnover) {

        if (currentRules == null) {
            currentRules = new AccountRulesPropertiesLoader(
                    RULES_PATH + "current.properties"
            );
        }

        double multiplier = currentRules.getDouble(
                "current.overdraft.multiplier", 2.5
        );

        double minimum = currentRules.getDouble(
                "current.overdraft.minimum", 25000.0
        );

        return Math.max(monthlyTurnover * multiplier, minimum);
    }

    // Fixed Deposit Rules

    public static double getFDInterestRate(int months) {

        if (fdRules == null) {
            fdRules = new AccountRulesPropertiesLoader(
                    RULES_PATH + "fixeddeposit.properties"
            );
        }

        int minimumTenure = (int) fdRules.getDouble(
                "fixeddeposit.minimumTenure", 12
        );

        double interestRate = fdRules.getDouble(
                "fixeddeposit.interestRate", 6.5
        );

        if (months >= minimumTenure) {
            return interestRate;
        }

        return 0.0;
    }

    // Salary Account Rules

    public static String getSalaryDefaultEmployer() {

        if (salaryRules == null) {
            salaryRules = new AccountRulesPropertiesLoader(
                    RULES_PATH + "salary.properties"
            );
        }

        return salaryRules.getProperty(
                "salary.defaultEmployer", "Infosys"
        );
    }
}