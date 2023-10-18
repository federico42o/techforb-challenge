package com.f42o.api.balance;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping
    public ResponseEntity<BalanceDTO> getBalanceOn(@RequestParam Long clientId, @RequestParam  LocalDateTime start, @RequestParam  LocalDateTime end,@RequestParam BalanceType balanceType){


        return ResponseEntity.status(HttpStatus.OK).body(balanceService.getBalanceByClientIdBetween(clientId,start,end,balanceType));
    }
}
