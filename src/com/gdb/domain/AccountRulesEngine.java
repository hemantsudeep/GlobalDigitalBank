package com.gdb.domain;

import java.util.HashMap;
import java.util.Map;

public class AccountRulesEngine {

    // =====================================================
    // SAVINGS ACCOUNT RULES
    // =====================================================

    private static final Map<String, Double> SAVINGS_MIN_BALANCE =
            new HashMap<>();

    private static final Map<String, Double> SAVINGS_INTEREST_RATE =
            new HashMap<>();

    static {

        // New: 0 to 1 year
        SAVINGS_MIN_BALANCE.put("NEW", 10000.0);
        SAVINGS_INTEREST_RATE.put("NEW", 2.70);

        // Standard: 1 to 3 years
        SAVINGS_MIN_BALANCE.put("STANDARD", 7500.0);
        SAVINGS_INTEREST_RATE.put("STANDARD", 3.00);

        // Premium: 3 to 5 years
        SAVINGS_MIN_BALANCE.put("PREMIUM", 5000.0);
        SAVINGS_INTEREST_RATE.put("PREMIUM", 3.50);

        // Privilege: 5+ years
        SAVINGS_MIN_BALANCE.put("PRIVILEGE", 2500.0);
        SAVINGS_INTEREST_RATE.put("PRIVILEGE", 4.00);
    }

    // =====================================================
    // SAVINGS MINIMUM BALANCE
    // =====================================================

    public static double getSavingsMinBalance(
            int tenureYears) {

        if (tenureYears >= 5) {

            return SAVINGS_MIN_BALANCE.get("PRIVILEGE");

        } else if (tenureYears >= 3) {

            return SAVINGS_MIN_BALANCE.get("PREMIUM");

        } else if (tenureYears >= 1) {

            return SAVINGS_MIN_BALANCE.get("STANDARD");

        } else {

            return SAVINGS_MIN_BALANCE.get("NEW");
        }
    }

    // =====================================================
    // SAVINGS INTEREST RATE
    // =====================================================

    public static double getSavingsInterestRate(
            int tenureYears) {

        if (tenureYears >= 5) {

            return SAVINGS_INTEREST_RATE.get("PRIVILEGE");

        } else if (tenureYears >= 3) {

            return SAVINGS_INTEREST_RATE.get("PREMIUM");

        } else if (tenureYears >= 1) {

            return SAVINGS_INTEREST_RATE.get("STANDARD");

        } else {

            return SAVINGS_INTEREST_RATE.get("NEW");
        }
    }

    // =====================================================
    // CURRENT ACCOUNT OVERDRAFT LIMIT
    // =====================================================

    public static double getCurrentOverdraftLimit(
            double monthlyTurnover) {

        double calculatedLimit =
                monthlyTurnover * 2.5;

        return Math.max(
                calculatedLimit,
                25000.0
        );
    }

    // =====================================================
    // FIXED DEPOSIT INTEREST RATE
    // =====================================================

    public static double getFDInterestRate(
            int months) {

        if (months >= 12) {

            return 6.5;

        }

        /*
         * The Activity 13.1 specification explicitly
         * defines the 6.5% rate for deposits of 12+
         * months. Rates for shorter durations are not
         * specified in the supplied README.
         */
        return 0.0;
    }
}