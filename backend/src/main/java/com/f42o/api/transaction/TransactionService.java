package com.f42o.api.transaction;

import com.f42o.api.account.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    void withdraw(WithdrawDTO dto);
    void deposit(DepositDTO dto);
    void transfer(TransferDTO dto);
    Page<TransactionDTO> getAllByClientId(Pageable pageable,Long clientId);


}
