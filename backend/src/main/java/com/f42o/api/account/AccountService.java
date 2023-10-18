package com.f42o.api.account;

import com.f42o.api.user.User;

public interface AccountService {

    void createBankAccount(BankAccount bankAccount);
    BankAccountResponse getAccountByClientId(Long id);
}
