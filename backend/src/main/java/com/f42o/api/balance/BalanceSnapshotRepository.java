package com.f42o.api.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BalanceSnapshotRepository extends JpaRepository<BalanceSnapshot,Long> {

    List<BalanceSnapshot> findAllByBankAccountIdAndTimestampBetweenOrderByTimestampAsc(Long id, LocalDateTime start,LocalDateTime end);
}
