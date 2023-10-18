package com.f42o.api.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Page<Transaction> findAllByClientId(Pageable pageable,Long id);

    @Query("select t from Transaction t where t.timestamp between ?1 and ?2 AND t.status = 'COMPLETED' AND (t.transactionType = 'DEPOSIT' OR t.transactionType = 'TRANSFER')")
    List<Transaction> findByDestinationAccount_IdAndTimestampBetween(Instant startDate, Instant endDate);

    @Query("SELECT t FROM Transaction t WHERE t.destinationAccount.id = ?1 AND t.timestamp BETWEEN ?2 AND ?3 AND t.status = 'COMPLETED' AND (t.transactionType = 'DEPOSIT' OR t.transactionType = 'TRANSFER')")
    List<Transaction> findByDestinationAccountIdAndTimestampBetween(UUID id, Instant startDate, Instant endDate);
}
