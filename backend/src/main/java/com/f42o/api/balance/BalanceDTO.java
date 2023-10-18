package com.f42o.api.balance;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;

@Data
@AllArgsConstructor
public class BalanceDTO {

    private String[] labels;
    private BigDecimal[] values;

    private BalanceDTO(int size,String[] labels){
        this.labels = labels;
        this.values = new BigDecimal[size];
        Arrays.fill(this.values, BigDecimal.ZERO);
    }
    public static BalanceDTO getBalanceDTO(BalanceType balanceType){
        return switch (balanceType){
            case WEEKLY -> new BalanceDTO(7, new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
            case MONTHLY -> new BalanceDTO(12, new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
            case DAILY -> new BalanceDTO(1,new String[1]);
        };
    }
}
