package com.f42o.api.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponse {

    private String fullName;
    private String cbu;
    private String alias;
    private BigDecimal balance;
}
