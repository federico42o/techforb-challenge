package com.f42o.api.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByClientId(Pageable pageable,Long id);

    @Query("SELECT t FROM Transaction t WHERE t.client.id = ?1 AND (t.transactionType IN ('DEPOSIT','TRANSFER')) AND (t.status = 'COMPLETED') AND t.destinationAccount.id=?2 AND t.amount > 0 AND MONTH(t.timestamp) = MONTH(?3) AND YEAR(t.timestamp) = YEAR(?3)")
    List<Transaction> findAllIncomes(Long id, Long bankAccountId, LocalDate date);

    @Query("SELECT t FROM Transaction t WHERE t.client.id = ?1 AND (t.transactionType IN ('WITHDRAW','TRANSFER','PAYMENT')) AND (t.status = 'COMPLETED') AND t.sourceAccount.id=?2 AND t.amount > 0 AND MONTH(t.timestamp) = MONTH(?3) AND YEAR(t.timestamp) = YEAR(?3)")
    List<Transaction> findAllExpenses(Long id, Long bankAccountId, LocalDate date);

}
