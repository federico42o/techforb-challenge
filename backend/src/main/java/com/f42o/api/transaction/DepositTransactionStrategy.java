package com.f42o.api.transaction;

import com.f42o.api.account.BankAccount;

import java.math.BigDecimal;

public class DepositTransactionStrategy implements TransactionStrategy{
    @Override
    public void performTransaction(Transaction transaction) {
        try{
            transaction.setStatus(TransactionStatus.PENDING);
            BankAccount destination = transaction.getDestinationAccount();
            BigDecimal amount = transaction.getAmount();

            destination.deposit(amount);
            transaction.setStatus(TransactionStatus.COMPLETED);
        }catch (RuntimeException ex){
            transaction.setStatus(TransactionStatus.CANCELLED);
        }
    }
}
