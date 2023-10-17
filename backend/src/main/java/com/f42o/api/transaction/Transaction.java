package com.f42o.api.transaction;

import com.f42o.api.account.BankAccount;
import com.f42o.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal amount;
    @ManyToOne
    private BankAccount sourceAccount;
    @ManyToOne
    private BankAccount destinationAccount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @CreationTimestamp
    private Instant timestamp;

    @ManyToOne
    private User client;

    public Transaction() {
    }

    public Transaction(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void executeTransaction() {
        TransactionStrategy transactionStrategy = getTransactionStrategy();
        transactionStrategy.performTransaction(this);
    }

    private TransactionStrategy getTransactionStrategy() {
        return switch (transactionType) {
            case WITHDRAW -> new WithdrawTransactionStrategy();
            case TRANSFER, PAYMENT -> new TransferTransactionStrategy();
            case DEPOSIT -> new DepositTransactionStrategy();
            default -> throw new UnsupportedOperationException("Transaction type not supported");
        };
    }

}
