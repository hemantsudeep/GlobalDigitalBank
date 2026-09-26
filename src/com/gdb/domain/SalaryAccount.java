package com.gdb.domain;

public class SalaryAccount extends Account {

    private String employerName;
    private int inactiveMonths;

    public SalaryAccount(String accountNumber, String name, int age,
                         double balance, String status, String pin,
                         String employerName) {

        super(accountNumber, name, age,
                balance, "SALARY", status, pin);

        this.employerName = employerName;
        this.inactiveMonths = 0;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public int getInactiveMonths() {
        return inactiveMonths;
    }

    public void incrementInactiveMonths() {
        inactiveMonths++;
    }
}