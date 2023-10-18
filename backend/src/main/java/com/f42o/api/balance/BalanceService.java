package com.f42o.api.balance;

import com.f42o.api.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public interface BalanceService {

    BigDecimal getIncomes(Long id);

}
