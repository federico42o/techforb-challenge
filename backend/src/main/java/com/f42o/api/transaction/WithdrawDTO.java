package com.f42o.api.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawDTO {
    private double amount;
    private String cbu;
    private Long clientId;
}
