
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

        String category = getTenureCategory(tenureYears);

        return savingsRules.getDouble(
                "savings." + category + ".minBalance",
                10000.0
        );
    }

    public static double getSavingsInterestRate(int tenureYears) {

        String category = getTenureCategory(tenureYears);

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

    // Activity 15: Daily Transfer Limits

    public static double getDailyTransferLimit(
            String accountType, int tenureYears) {

        if (accountType == null) {
            return 0.0;
        }

        String type = accountType
                .toLowerCase()
                .replaceAll("[^a-z]", "");

        String category = getTenureCategory(tenureYears);
        AccountRulesPropertiesLoader rules;
        String prefix;

        if (type.contains("savings")) {
            rules = savingsRules;
            prefix = "savings";

        } else if (type.contains("current")) {
            if (currentRules == null) {
                currentRules = new AccountRulesPropertiesLoader(
                        RULES_PATH + "current.properties"
                );
            }

            rules = currentRules;
            prefix = "current";

        } else if (type.contains("fixeddeposit")
                || type.equals("fd")) {

            if (fdRules == null) {
                fdRules = new AccountRulesPropertiesLoader(
                        RULES_PATH + "fixeddeposit.properties"
                );
            }

            rules = fdRules;
            prefix = "fixeddeposit";

        } else if (type.contains("salary")) {
            if (salaryRules == null) {
                salaryRules = new AccountRulesPropertiesLoader(
                        RULES_PATH + "salary.properties"
                );
            }

            rules = salaryRules;
            prefix = "salary";

        } else {
            return 0.0;
        }

        return rules.getDouble(
                prefix + "." + category + ".daily.transfer.limit",
                0.0
        );
    }

    // Helper: Determine account tenure category

    private static String getTenureCategory(int tenureYears) {

        if (tenureYears >= 5) {
            return "privilege";
        } else if (tenureYears >= 3) {
            return "premium";
        } else if (tenureYears >= 1) {
            return "standard";
        } else {
            return "new";
        }
    }
}
