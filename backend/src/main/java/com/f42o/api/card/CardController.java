package com.f42o.api.card;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService service;

    @GetMapping("{id}")
    public ResponseEntity<List<CardDTO>> getAllByCredentialNumber(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(service.getAllByClientId(id));
    }
}
