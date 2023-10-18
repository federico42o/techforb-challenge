package com.f42o.api.account;

import java.util.Optional;

public interface AccountService {

    BankAccountResponse getAccountByClientId(Long id);
}
