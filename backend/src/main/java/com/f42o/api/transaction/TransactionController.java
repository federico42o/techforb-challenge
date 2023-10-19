package com.f42o.api.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;


    @GetMapping("{clientId}")
    public ResponseEntity<Page<TransactionDTO>> getAllByClientId(Pageable pageable, @PathVariable Long clientId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllByClientId(pageable, clientId));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<HttpStatus> withdraw(@RequestBody WithdrawDTO dto){
        service.withdraw(dto);

        return ResponseEntity.noContent().build();
    }
    @PostMapping("/transfer")
    public ResponseEntity<HttpStatus> withdraw(@RequestBody TransferDTO dto){
        service.transfer(dto);

        return ResponseEntity.noContent().build();
    }



    @PostMapping("/deposit")
    public ResponseEntity<HttpStatus> withdraw(@RequestBody DepositDTO dto){
        service.deposit(dto);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/incomes/{id}")
    public ResponseEntity<MonthlyTransactionDTO> incomes(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllIncomes(id));
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<MonthlyTransactionDTO> expenses(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllExpenses(id));
    }

}
