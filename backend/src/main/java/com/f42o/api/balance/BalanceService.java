package com.f42o.api.balance;

import com.f42o.api.account.BankAccount;
import com.f42o.api.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface BalanceService {

    void snapshotBalance(BankAccount bankAccount);
    BalanceDTO getBalanceByClientIdBetween(Long clientId, LocalDateTime start, LocalDateTime end,BalanceType balanceType);



}
