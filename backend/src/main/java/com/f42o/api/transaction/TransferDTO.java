package com.f42o.api.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    private double amount;
    private String sourceCbu;
    private String destinationCbu;
    private Long clientId;
}
