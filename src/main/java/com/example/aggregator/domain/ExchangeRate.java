package com.example.aggregator.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Currency   currencyFrom;
    private Currency   currencyTo;
    private BigDecimal rate;

    ExchangeRate() {
    }

    public ExchangeRate(long id, Currency currencyFrom, Currency currencyTo,
                        BigDecimal rate) {
        this.id = id;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    public Currency getCurrencyTo() {
        return currencyTo;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExchangeRate [id=")
               .append(id)
               .append(", currencyFrom=")
               .append(currencyFrom)
               .append(", currencyTo=")
               .append(currencyTo)
               .append(", rate=")
               .append(rate)
               .append("]");
        return builder.toString();
    }
}
