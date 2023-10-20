package com.f42o.api.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDTO {
    @Min(1)
    private BigDecimal amount;
    @NotBlank
    private String sourceCbu;
    @NotBlank
    private String destinationCbu;
    @NotNull
    private Long clientId;
}
