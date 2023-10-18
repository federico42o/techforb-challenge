package com.f42o.api.card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByBankAccountClientId(Long id);
}
