package com.f42o.api.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositDTO {

    private String sourceCbu;
    private String destinationCbu;
    private double amount;
    private Long clientId;

}
