package com.f42o.api.balance;

import com.f42o.api.account.BankAccount;

import java.time.LocalDateTime;


public interface BalanceService {

    void snapshotBalance(BankAccount bankAccount);
    BalanceDTO getBalanceByClientIdBetween(Long clientId, LocalDateTime start, LocalDateTime end,BalanceType balanceType);





}
