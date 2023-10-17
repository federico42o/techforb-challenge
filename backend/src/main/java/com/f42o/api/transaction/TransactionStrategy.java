package com.f42o.api.transaction;

public interface TransactionStrategy {
    void performTransaction(Transaction transaction);
}
