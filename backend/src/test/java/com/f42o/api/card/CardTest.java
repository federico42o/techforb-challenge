package com.f42o.api.card;

import com.f42o.api.account.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardTest {


    @Mock
    BankAccount bankAccount;

    Card card;
    @BeforeEach
    public void setUp() {

        card = new Card("Juan Perez",bankAccount);

    }


    @Test
    public void shouldBeAValidCVV(){
        assertEquals(3, card.getCVV().length());
    }

    @Test
    public void shouldBeAValidCardNumber(){
        assertEquals(16, card.getCardNumber().length());
    }

    @Test
    public void expireDateMustBeInFiveYears(){
        LocalDate expected = LocalDate.now().plus(Period.ofYears(5));
        assertEquals(expected, card.getExpirationDate());
    }

    @Test
    public void cardShouldBeEnabled(){
        assertTrue(card.isEnabled());
    }

}