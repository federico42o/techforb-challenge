package com.f42o.api.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    void withdraw(WithdrawDTO dto);
    void deposit(DepositDTO dto);
    void transfer(TransferDTO dto);
    Page<TransactionDTO> getAllByClientId(Pageable pageable,Long clientId);

    MonthlyTransactionDTO getAllIncomes(Long id);
    MonthlyTransactionDTO getAllExpenses(Long id);



}
