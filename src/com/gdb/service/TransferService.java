
package com.gdb.service;

import com.gdb.domain.AbstractAccount;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InactiveAccountException;
import com.gdb.exceptions.InsufficientBalanceException;
import com.gdb.exceptions.InvalidPinException;
import com.gdb.exceptions.InvalidAmountException;

public class TransferService {

    public void transfer(
            AbstractAccount from,
            AbstractAccount to,
            double amount,
            String pin) throws AccountException {

        // 1. Validate source and destination accounts
        if (from == null || to == null) {
            throw new AccountException(
                    "Source and destination accounts are required");
        }

        // 2. Validate account status
        if (!from.isActive() || !to.isActive()) {
            throw new InactiveAccountException(
                    "Both accounts must be active to transfer funds");
        }

        // 3. Validate sender PIN
        if (!from.validatePin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }

        // 4. Validate transfer amount and available balance
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidAmountException(
                    "Transfer amount must be positive and finite");
        }

        if (!from.canWithdraw(amount)) {
            throw new InsufficientBalanceException(
                    "Insufficient balance for transfer of Rs. " + amount);
        }

        // 5. Check sender's daily transfer limit
        from.resetDailyTransferIfNeeded();

        if (!from.canTransfer(amount)) {
            throw new AccountException(
                    "Daily transfer limit exceeded. Remaining today: Rs. "
                            + from.getRemainingDailyTransferLimit());
        }

        // 6. Withdraw from the sender
        from.withdraw(amount, pin);

        // 7. Deposit into the recipient
        try {
            to.deposit(amount);
        } catch (InvalidAmountException e) {
            // Restore the sender's balance if the deposit fails
            try {
                from.deposit(amount);
            } catch (InvalidAmountException rollbackError) {
                e.addSuppressed(rollbackError);
            }

            throw e;
        } catch (RuntimeException e) {
            // Attempt to restore the sender's balance
            try {
                from.deposit(amount);
            } catch (InvalidAmountException rollbackError) {
                e.addSuppressed(rollbackError);
            }

            throw e;
        }

        // 8. Record only successful transfers
        from.updateDailyTransferTotal(amount);
    }
}
