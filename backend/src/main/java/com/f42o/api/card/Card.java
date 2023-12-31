package com.f42o.api.card;

import com.f42o.api.account.BankAccount;
import com.f42o.api.utils.Utils;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cardHolderName;
    private String CVV;
    private final String ISSUER = "4007";

    private LocalDateTime createdAt;
    private boolean isEnabled;
    @ManyToOne
    private BankAccount bankAccount;


    public Card() {
    }

    public Card(String cardHolderName,BankAccount bankAccount) {
        this.cardHolderName =  shortName(cardHolderName);
        this.cardNumber = generateCardNumber();
        this.CVV = generateCVV();
        this.expirationDate = LocalDate.now().plus(Period.ofYears(5));
        this.isEnabled = true;
        this.bankAccount = bankAccount;
        this.createdAt = LocalDateTime.now();
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

    private String shortName(String cardHolderName){
        String[] names = cardHolderName.split(" ");
        String newName = "";
        for(String name:names){
            newName += name +" ";
            if(newName.length()+name.length()>16){
                break;
            }
        }
        return newName.trim();
    }


}
