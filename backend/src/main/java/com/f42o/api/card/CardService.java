package com.f42o.api.card;

import com.f42o.api.account.BankAccount;

import java.util.List;

public interface CardService {

    void createCard(Card card);
    List<CardDTO> getAllByClientId(Long id);
}
