package com.f42o.api.transaction;

import com.f42o.api.account.AccountRepository;
import com.f42o.api.account.BankAccount;
import com.f42o.api.balance.BalanceService;
import com.f42o.api.balance.BalanceSnapshot;
import com.f42o.api.balance.BalanceSnapshotRepository;
import com.f42o.api.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    TransactionServiceImpl transactionService;
    @Mock
    BalanceService balanceService;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    AccountRepository accountRepository;


    User client,client2;
    BankAccount bankAccount,bankAccount2;
    WithdrawDTO withdraw;
    DepositDTO deposit;
    TransferDTO transfer;
    Transaction withdrawTransaction,transferTransaction,depositTransaction;
    @Mock
    BalanceSnapshotRepository snapshotRepository;
    @Mock
    BalanceSnapshot balanceSnapshot;


    @BeforeEach
    void setUp(){
        client = new User();
        client.setId(1L);

        bankAccount = new BankAccount(client);
        bankAccount.setId(1L);
        bankAccount.setBalance(BigDecimal.valueOf(5000));

        client2 = new User();
        client2.setId(2L);

        bankAccount2 = new BankAccount(client2);
        bankAccount2.setId(2L);
        bankAccount2.setBalance(BigDecimal.valueOf(15000));

        withdraw = WithdrawDTO.builder()
                .cbu(bankAccount.getCBU())
                .clientId(bankAccount.getClient().getId())
                .build();
        deposit = DepositDTO.builder()
                .destinationCbu(bankAccount2.getCBU())
                .clientId(bankAccount2.getClient().getId())
                .build();
        transfer = TransferDTO.builder()
                .sourceCbu(bankAccount.getCBU())
                .destinationCbu(bankAccount2.getCBU())
                .clientId(bankAccount.getClient().getId())
                .build();
        withdrawTransaction = new Transaction(TransactionType.WITHDRAW);
        withdrawTransaction.setId(1L);
        transferTransaction = new Transaction(TransactionType.TRANSFER);
        transferTransaction.setId(1L);
        depositTransaction = new Transaction(TransactionType.DEPOSIT);
        depositTransaction.setId(1L);
    }

    @Test
    void shouldCannotWithdraw(){


        when(accountRepository.findByCBU(bankAccount.getCBU())).thenReturn(Optional.of(bankAccount));
        BigDecimal amount = BigDecimal.valueOf(10000);
        withdraw.setAmount(amount);
        BigDecimal expected = bankAccount.getBalance();

        transactionService.withdraw(withdraw);

        verify(accountRepository).save(bankAccount);
        assertEquals(expected,bankAccount.getBalance());
    }

    @Test
    void shouldCanWithdraw(){


        when(accountRepository.findByCBU(bankAccount.getCBU())).thenReturn(Optional.of(bankAccount));
        BigDecimal amount = BigDecimal.valueOf(100);
        withdraw.setAmount(amount);
        BigDecimal expected = bankAccount.getBalance().subtract(amount);

        transactionService.withdraw(withdraw);

        verify(accountRepository).save(bankAccount);

        assertEquals(expected,bankAccount.getBalance());
    }

    @Test
    void shouldCanDeposit() {

        when(accountRepository.findByCBU(bankAccount2.getCBU())).thenReturn(Optional.of(bankAccount2));
        BigDecimal amount = BigDecimal.valueOf(100);
        deposit.setAmount(amount);
        BigDecimal expected = bankAccount2.getBalance().add(amount);

        transactionService.deposit(deposit);

        verify(accountRepository).save(bankAccount2);

        assertEquals(expected,bankAccount2.getBalance());
    }
    @Test
    void shouldCannotDeposit() {

        when(accountRepository.findByCBU(bankAccount2.getCBU())).thenReturn(Optional.of(bankAccount2));
        BigDecimal amount = BigDecimal.valueOf(-1000000);
        deposit.setAmount(amount);
        BigDecimal expected = bankAccount2.getBalance();

        transactionService.deposit(deposit);

        verify(accountRepository).save(bankAccount2);

        assertEquals(expected,bankAccount2.getBalance());
    }

    @Test
    void shouldCanTransfer() {
        when(accountRepository.findByCBU(bankAccount.getCBU())).thenReturn(Optional.of(bankAccount));
        when(accountRepository.findByCBU(bankAccount2.getCBU())).thenReturn(Optional.of(bankAccount2));
        BigDecimal amount = BigDecimal.valueOf(1000);
        transfer.setAmount(amount);
        BigDecimal sourceExpected = bankAccount.getBalance().subtract(amount);
        BigDecimal destinationExpected = bankAccount2.getBalance().add(amount);

        transactionService.transfer(transfer);

        verify(accountRepository).save(bankAccount);
        verify(accountRepository).save(bankAccount2);

        assertEquals(sourceExpected,bankAccount.getBalance());
        assertEquals(destinationExpected,bankAccount2.getBalance());
    }
    @Test
    void shouldCannotTransfer() {
        when(accountRepository.findByCBU(bankAccount.getCBU())).thenReturn(Optional.of(bankAccount));
        when(accountRepository.findByCBU(bankAccount2.getCBU())).thenReturn(Optional.of(bankAccount2));
        BigDecimal amount = BigDecimal.valueOf(10000000);
        transfer.setAmount(amount);
        BigDecimal sourceExpected = bankAccount.getBalance();
        BigDecimal destinationExpected = bankAccount2.getBalance();

        transactionService.transfer(transfer);

        verify(accountRepository).save(bankAccount);
        verify(accountRepository).save(bankAccount2);

        assertEquals(sourceExpected,bankAccount.getBalance());
        assertEquals(destinationExpected,bankAccount2.getBalance());
    }
}