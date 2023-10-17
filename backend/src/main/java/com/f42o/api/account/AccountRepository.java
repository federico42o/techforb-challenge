package com.f42o.api.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<BankAccount, UUID> {
    Optional<BankAccount> findByCBU(String cbu);
}
