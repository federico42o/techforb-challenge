package com.f42o.api.balance;

import com.f42o.api.account.AccountRepository;
import com.f42o.api.account.BankAccount;
import com.f42o.api.transaction.Transaction;
import com.f42o.api.transaction.TransactionRepository;
import com.f42o.api.transaction.TransactionStatus;
import com.f42o.api.transaction.TransactionType;
import com.f42o.api.user.User;
import com.f42o.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceServiceImpl implements BalanceService{
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;


    public BigDecimal getIncomesUntil(BankAccount destinationAccount, Instant startDate, Instant endDate){
        BigDecimal incomes = BigDecimal.ZERO;
        List<Transaction> transactions = transactionRepository.findByDestinationAccount_IdAndTimestampBetween(startDate,endDate);
        for (Transaction transaction : transactions) {
            if(transaction.getDestinationAccount().equals(destinationAccount)){
                incomes = incomes.add(transaction.getAmount());
            }
        }

        return incomes;
    }

    @Override
    public BigDecimal getIncomes(Long id) {

        User user = userRepository.findById(id).orElseThrow();
        BankAccount bankAccount = accountRepository.findByClientId(user.getId()).orElseThrow();
        return getIncomesUntil(bankAccount,bankAccount.getCreatedAt(),Instant.now());
    }
}
