package com.example.aggregator.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OriginalAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private BigDecimal amount;
    private Currency   currency;

    OriginalAmount() {
    }

    public OriginalAmount(long id, BigDecimal amount, Currency currency) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OriginalAmount [id=")
               .append(id)
               .append(", amount=")
               .append(amount)
               .append(", currency=")
               .append(currency)
               .append("]");
        return builder.toString();
    }
}
