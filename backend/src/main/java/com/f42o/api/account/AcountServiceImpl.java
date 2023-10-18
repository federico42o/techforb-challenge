package com.f42o.api.account;

import com.f42o.api.user.User;
import com.f42o.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public BankAccountResponse getAccountByClientId(Long id) {
        userRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Client doesn't exist.")
        );
        BankAccount bankAccount =accountRepository.findByClientId(id).orElseThrow(
                ()->new RuntimeException("This client doesn't have an account.")
        );
        return BankAccountResponse.builder()
                .alias(bankAccount.getALIAS())
                .fullName(bankAccount.getClient().getFullName())
                .balance(bankAccount.getBalance())
                .cbu(bankAccount.getCBU())
                .build();
    }
}
