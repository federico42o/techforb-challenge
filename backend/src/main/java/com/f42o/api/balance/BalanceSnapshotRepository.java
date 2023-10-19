package com.f42o.api.balance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BalanceSnapshotRepository extends JpaRepository<BalanceSnapshot,Long> {

    List<BalanceSnapshot> findAllByBankAccountIdAndTimestampBetweenOrderByTimestampAsc(Long id, LocalDateTime start,LocalDateTime end);
}
