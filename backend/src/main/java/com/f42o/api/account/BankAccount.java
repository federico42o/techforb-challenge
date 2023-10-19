package com.f42o.api.account;

import com.f42o.api.user.User;
import com.f42o.api.utils.Utils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BankAccount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String CBU;
    private String ALIAS;
    @ManyToOne
    private User client;
    private BigDecimal balance;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private boolean isEnabled;
    public BankAccount() {
        this.balance = new BigDecimal(0);
    }

    public BankAccount(User client) {
        this.client = client;
        this.CBU = generateCBU();
        this.ALIAS = generateAlias();
        this.balance = new BigDecimal(0);
        this.isEnabled = true;
    }


    public BigDecimal getBalance() {
        if(balance == null){
            this.balance = new BigDecimal(0);
        }
        return balance;
    }
    private String generateCBU() {
        StringBuilder CBU = new StringBuilder();
        for (int i=0;i<20;i++){
            CBU.append(Utils.randomNumber());
        }
        return CBU.toString();
    }

    private String generateAlias() {
        StringBuilder alias = new StringBuilder();
        for (int i=0;i<3;i++){
            alias.append(Utils.randomWord());
            if(i!=2){
                alias.append(".");
            };
        }
        return alias.toString();
    }


    public void setBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = Objects.requireNonNull(balance);
        }
    }

    public void deposit(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = balance.add(Objects.requireNonNull(amount));
        }else{
            throw new RuntimeException("Invalid amount");
        }
    }

    public void withdraw(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(this.balance) <= 0) {
            this.balance = balance.subtract(Objects.requireNonNull(amount));
        }else{
            throw new RuntimeException("Insuficient Founds");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BankAccount other = (BankAccount) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
