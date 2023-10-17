package com.f42o.api.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {


    private String cardNumber;
    private LocalDate expirationDate;
    private String cardHolderName;
    private Instant createdAt;
    private String cvv;
    private String issuer;
}
