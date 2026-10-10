
package com.gdb.service;

import com.gdb.domain.AbstractAccount;
import com.gdb.domain.Transaction;
import com.gdb.domain.TransactionType;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InactiveAccountException;
import com.gdb.exceptions.InsufficientBalanceException;
import com.gdb.exceptions.InvalidPinException;
import com.gdb.exceptions.InvalidAmountException;

public class TransferService {

    // Existing Activity 15 method retained for backward compatibility.
    public void transfer(
            AbstractAccount from,
            AbstractAccount to,
            double amount,
            String pin) throws AccountException {

        validateTransfer(from, to, amount, pin);

        from.resetDailyTransferIfNeeded();

        if (!from.canTransfer(amount)) {
            throw new AccountException(
                    "Daily transfer limit exceeded. Remaining today: Rs. "
                            + from.getRemainingDailyTransferLimit());
        }

        from.withdraw(amount, pin);

        try {
            to.deposit(amount);
        } catch (InvalidAmountException | RuntimeException e) {
            // Roll back the debit if crediting the destination fails.
            try {
                from.deposit(amount);
            } catch (InvalidAmountException rollbackError) {
                e.addSuppressed(rollbackError);
            }

            throw e;
        }

        from.updateDailyTransferTotal(amount);
    }

    // Activity 16: Perform a transfer and return its transaction record.
    public Transaction transferWithTransaction(
            AbstractAccount from,
            AbstractAccount to,
            double amount,
            String pin) throws AccountException {

        transfer(from, to, amount, pin);

        return new Transaction(
                TransactionType.TRANSFER,
                amount,
                from.getAccountNumber(),
                "Transfer from account "
                        + from.getAccountNumber()
                        + " to account "
                        + to.getAccountNumber());
    }

    private void validateTransfer(
            AbstractAccount from,
            AbstractAccount to,
            double amount,
            String pin) throws AccountException {

        if (from == null || to == null) {
            throw new AccountException(
                    "Source and destination accounts are required");
        }

        if (from == to
                || from.getAccountNumber().equals(to.getAccountNumber())) {
            throw new AccountException(
                    "Source and destination accounts must be different");
        }

        if (!from.isActive() || !to.isActive()) {
            throw new InactiveAccountException(
                    "Both accounts must be active to transfer funds");
        }

        if (!from.validatePin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidAmountException(
                    "Transfer amount must be positive and finite");
        }

        if (!from.canWithdraw(amount)) {
            throw new InsufficientBalanceException(
                    "Insufficient balance for transfer of Rs. " + amount);
        }
    }
}
