package com.f42o.api.transaction;

import com.f42o.api.account.BankAccount;
import com.f42o.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
            case TRANSFER-> new TransferTransactionStrategy();
            case DEPOSIT -> new DepositTransactionStrategy();
            default -> throw new UnsupportedOperationException("Transaction type not supported");
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Transaction other = (Transaction) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
