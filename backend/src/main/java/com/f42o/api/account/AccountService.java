package com.f42o.api.account;

public interface AccountService {

    void createBankAccount(BankAccount bankAccount);
    BankAccountResponse getAccountByClientId(Long id);
}
