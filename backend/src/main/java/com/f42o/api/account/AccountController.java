package com.f42o.api.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {


    private final AccountService service;

    @GetMapping("{clientId}")
    public ResponseEntity<BankAccountResponse> getByClientId(@PathVariable Long clientId){

        return ResponseEntity.status(HttpStatus.OK).body(service.getAccountByClientId(clientId));
    }
}
