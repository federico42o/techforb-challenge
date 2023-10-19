package com.f42o.api.transaction;

import com.f42o.api.account.BankAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private TransactionType transactionType;
    private BigDecimal amount;
    private BankAccountDTO sourceAccount;
    private BankAccountDTO destinationAccount;
    private TransactionStatus status;
    private Instant timestamp;
}
