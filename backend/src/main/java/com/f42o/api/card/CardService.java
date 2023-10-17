package com.f42o.api.card;

import java.util.List;

public interface CardService {

    List<CardDTO> getAllByClientId(Long id);
}
