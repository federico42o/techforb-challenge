package com.f42o.api.account;

import com.f42o.api.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class BankAccountTest {


    @Mock
    private User user;

    @BeforeEach
    public void inicializaMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldBeAValidAlias() {
        BankAccount account = new BankAccount(user);
        assertEquals(3, account.getALIAS().split("\\.").length);
    }
    @Test
    public void shouldBeAValidCBU() {
        BankAccount account = new BankAccount(user);
        System.out.println(account.getCBU());
        assertEquals(20, account.getCBU().length());
    }

    @Test
    public void shouldHaveAlias(){
        BankAccount account = new BankAccount(user);
        assertNotNull(account.getALIAS());
    }
    @Test
    public void shouldHaveCBU(){
        BankAccount account = new BankAccount(user);
        assertNotNull(account.getCBU());
    }

    @Test
    public void shouldHaveClient(){
        BankAccount account = new BankAccount(user);
        assertNotNull(account.getClient());
    }

    @Test
    public void balanceShouldNotBeNull(){
        BankAccount account = new BankAccount(user);
        assertNotNull(account.getBalance());
    }
    @Test
    public void shouldNotSetNegativeBalance() {
        BankAccount account = new BankAccount(user);
        account.setBalance(new BigDecimal(-12));
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }


    @Test
    public void shouldSetPositiveBalance() {
        BankAccount account = new BankAccount(user);
        account.setBalance(new BigDecimal(1200));
        assertEquals(BigDecimal.valueOf(1200), account.getBalance());
    }


    @Test
    public void shouldNotDepositNegativeAmount(){
        BankAccount account = new BankAccount(user);
        BigDecimal expected = account.getBalance();
        account.deposit(new BigDecimal(-1299));
        assertEquals(expected,account.getBalance());
    }

    @Test
    public void shouldDepositPositiveAmount(){
        BankAccount account = new BankAccount(user);
        BigDecimal amount = BigDecimal.valueOf(1500);
        BigDecimal expected = BigDecimal.valueOf(1500);
        account.deposit(amount);
        assertEquals(expected,account.getBalance());

    }

    @Test
    public void shouldWithdrawValidAmount(){
        BankAccount account = new BankAccount(user);
        BigDecimal amount = BigDecimal.valueOf(1500);
        BigDecimal expected = BigDecimal.valueOf(1500);
        account.setBalance(BigDecimal.valueOf(3000));
        account.withdraw(amount);
        assertEquals(expected,account.getBalance());
    }

    @Test
    public void shouldCannotWithdraw(){
        BankAccount account = new BankAccount(user);
        BigDecimal amount = BigDecimal.valueOf(1500);
        account.setBalance(BigDecimal.valueOf(1200));
        BigDecimal expected = account.getBalance();
        account.withdraw(amount);
        assertEquals(expected,account.getBalance());
    }

}