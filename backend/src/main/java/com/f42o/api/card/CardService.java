package com.f42o.api.card;

import java.util.List;

public interface CardService {

    void createCard(Card card);
    List<CardDTO> getAllByClientId(Long id);
}
