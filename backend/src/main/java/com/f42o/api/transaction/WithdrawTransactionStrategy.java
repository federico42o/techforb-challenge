package com.f42o.api.transaction;

import com.f42o.api.account.BankAccount;

public class WithdrawTransactionStrategy implements TransactionStrategy {
    @Override
    public void performTransaction(Transaction transaction) {
        try{
            transaction.setStatus(TransactionStatus.PENDING);
            BankAccount source = transaction.getSourceAccount();

            source.withdraw(transaction.getAmount());

            transaction.setStatus(TransactionStatus.COMPLETED);
        }catch (RuntimeException ex){
            transaction.setStatus(TransactionStatus.CANCELLED);
        }
    }
}
