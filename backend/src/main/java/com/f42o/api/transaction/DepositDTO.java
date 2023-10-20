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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositDTO {

    @NotBlank
    private String destinationCbu;
    @Min(1)
    private BigDecimal amount;
    @NotNull
    private Long clientId;

}
