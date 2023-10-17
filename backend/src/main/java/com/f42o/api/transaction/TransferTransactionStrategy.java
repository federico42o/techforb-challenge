package com.f42o.api.transaction;

import com.f42o.api.account.BankAccount;

import java.math.BigDecimal;

public class TransferTransactionStrategy implements TransactionStrategy {
    @Override
    public void performTransaction(Transaction transaction) {
        try{
        transaction.setStatus(TransactionStatus.PENDING);

        BankAccount source = transaction.getSourceAccount();
        BankAccount destination = transaction.getDestinationAccount();

        BigDecimal amount = transaction.getAmount();

        source.withdraw(amount);
        destination.deposit(amount);

        transaction.setStatus(TransactionStatus.COMPLETED);

        }catch(RuntimeException ex){
            transaction.setStatus(TransactionStatus.CANCELLED);
        }

    }
}
