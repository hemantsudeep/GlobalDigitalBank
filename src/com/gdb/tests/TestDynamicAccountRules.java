package com.gdb.tests;

import com.gdb.domain.*;

public class TestDynamicAccountRules {

    public static void main(String[] args) {

        System.out.println(
                "=== Activity 13.2: Dynamic Account Rules Test ==="
        );

        int[] tenures = {0, 2, 4, 6};

        for (int tenure : tenures) {

            SavingsAccount account =
                    (SavingsAccount) AccountFactory.createAccount(
                            "SAVINGS",
                            "SAV" + tenure,
                            "Hemant",
                            19,
                            20000.0,
                            "ACTIVE",
                            "1234",
                            tenure
                    );

            System.out.println(
                    "Tenure: " + account.getTenureYears()
                            + " yrs | Min Balance: Rs "
                            + account.getMinBalance()
                            + " | Interest Rate: "
                            + account.getInterestRate() + "%"
            );
        }

        System.out.println(
                "Dynamic rule integration verified!"
        );
    }
}