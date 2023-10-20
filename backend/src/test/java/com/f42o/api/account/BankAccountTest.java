package com.f42o.api.account;

import com.f42o.api.exception.InsufficientFundsException;
import com.f42o.api.exception.InvalidAmountException;
import com.f42o.api.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BankAccountTest {


    @Mock
    private User user;
    BankAccount bankAccount;
    @BeforeEach
    public void setUp() {
        bankAccount = new BankAccount(user);
    }

    @Test
    public void shouldBeAValidAlias() {
        assertEquals(3, bankAccount.getALIAS().split("\\.").length);
    }
    @Test
    public void shouldBeAValidCBU() {
        assertEquals(20, bankAccount.getCBU().length());
    }

    @Test
    public void shouldHaveAlias(){
        assertNotNull(bankAccount.getALIAS());
    }
    @Test
    public void shouldHaveCBU(){
        assertNotNull(bankAccount.getCBU());
    }

    @Test
    public void shouldHaveClient(){
        assertNotNull(bankAccount.getClient());
    }

    @Test
    public void balanceShouldNotBeNull(){
        assertNotNull(bankAccount.getBalance());
    }
    @Test
    public void shouldNotSetNegativeBalance() {
        bankAccount.setBalance(new BigDecimal(-12));
        assertEquals(BigDecimal.ZERO, bankAccount.getBalance());
    }


    @Test
    public void shouldSetPositiveBalance() {
        bankAccount.setBalance(new BigDecimal(1200));
        assertEquals(BigDecimal.valueOf(1200), bankAccount.getBalance());
    }


    @Test
    public void shouldNotDepositNegativeAmount(){
        BigDecimal amount = BigDecimal.valueOf(-1299);
        BigDecimal expected = bankAccount.getBalance();
        assertThrows(InvalidAmountException.class,()->
                bankAccount.deposit(amount)
        );
        assertEquals(expected,bankAccount.getBalance());
    }

    @Test
    public void shouldDepositPositiveAmount(){
        BigDecimal amount = BigDecimal.valueOf(1500);
        BigDecimal expected = BigDecimal.valueOf(1500);
        bankAccount.deposit(amount);
        assertEquals(expected,bankAccount.getBalance());

    }

    @Test
    public void shouldWithdrawValidAmount(){
        BigDecimal amount = BigDecimal.valueOf(1500);
        BigDecimal expected = BigDecimal.valueOf(1500);
        bankAccount.setBalance(BigDecimal.valueOf(3000));
        bankAccount.withdraw(amount);
        assertEquals(expected,bankAccount.getBalance());
    }

    @Test
    public void shouldCannotWithdraw(){
        BigDecimal amount = BigDecimal.valueOf(1500);
        bankAccount.setBalance(BigDecimal.valueOf(1200));
        BigDecimal expected = bankAccount.getBalance();
        assertThrows(InsufficientFundsException.class,()->
                bankAccount.withdraw(amount)
        );

        assertEquals(expected,bankAccount.getBalance());
    }

}