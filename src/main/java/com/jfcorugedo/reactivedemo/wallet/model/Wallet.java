package com.jfcorugedo.reactivedemo.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Wallet {

    @Id
    private String id;
    private BigDecimal balance;

    public static Wallet empty() {
        return new Wallet(null, BigDecimal.ZERO);
    }

    public Wallet withId(String id) {
        return new Wallet(id, this.balance);
    }
}
