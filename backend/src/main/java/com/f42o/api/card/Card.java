package com.f42o.api.card;

import com.f42o.api.account.BankAccount;
import com.f42o.api.utils.Utils;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Entity
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cardHolderName;
    private String CVV;
    private final String ISSUER = "4007";

    private Instant createdAt;
    private boolean isEnabled;
    @ManyToOne
    private BankAccount bankAccount;


    public Card() {
    }

    public Card(String cardHolderName,BankAccount bankAccount) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = generateCardNumber();
        this.CVV = generateCVV();
        this.expirationDate = LocalDate.now().plus(Period.ofYears(5));
        this.isEnabled = true;
        this.bankAccount = bankAccount;
        this.createdAt = Instant.now();
    }


    private String generateCardNumber(){
        StringBuilder sb = new StringBuilder(16);
        sb.append(ISSUER);
        for (int i = 0; i < 12; i++) {
            sb.append(Utils.randomNumber());
        }
        return sb.toString();

    }

    private String generateCVV(){
        StringBuilder sb = new StringBuilder(3);
        for (int i = 0; i < 3; i++) {
            sb.append(Utils.randomNumber());
        }
        return sb.toString();
    }


}
