package com.f42o.api.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByCBU(String cbu);
    Optional<BankAccount> findByClientId(Long id);
}
