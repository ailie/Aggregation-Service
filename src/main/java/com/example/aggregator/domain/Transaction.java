package com.example.aggregator.domain;

import static javax.persistence.CascadeType.ALL;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Transaction {

    @Id
    private String id;
    private String accountId;

    @OneToOne(cascade = { ALL })
    private ExchangeRate   exchangeRate;
    @OneToOne(cascade = { ALL })
    private OriginalAmount originalAmount;
    @OneToOne(cascade = { ALL })
    private Creditor       creditor;
    @OneToOne(cascade = { ALL })
    private Debtor         debtor;

    private String     status;
    private Currency   currency;
    private BigDecimal amount;
    private String     update;
    private String     description;

    Transaction() {
    }

    public Transaction(String id, String accountId, ExchangeRate exchangeRate,
                       OriginalAmount originalAmount, Creditor creditor, Debtor debtor,
                       String status, Currency currency, BigDecimal amount, String update,
                       String description) {
        this.id = id;
        this.accountId = accountId;
        this.exchangeRate = exchangeRate;
        this.originalAmount = originalAmount;
        this.creditor = creditor;
        this.debtor = debtor;
        this.status = status;
        this.currency = currency;
        this.amount = amount;
        this.update = update;
        this.description = description;
    }

    public String getId() {
        return id;
    }


    public String getAccountId() {
        return accountId;
    }


    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }


    public OriginalAmount getOriginalAmount() {
        return originalAmount;
    }


    public Creditor getCreditor() {
        return creditor;
    }


    public Debtor getDebtor() {
        return debtor;
    }


    public String getStatus() {
        return status;
    }


    public Currency getCurrency() {
        return currency;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public String getUpdate() {
        return update;
    }


    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Transaction [id=")
               .append(id)
               .append(", accountId=")
               .append(accountId)
               .append(", exchangeRate=")
               .append(exchangeRate)
               .append(", originalAmount=")
               .append(originalAmount)
               .append(", creditor=")
               .append(creditor)
               .append(", debtor=")
               .append(debtor)
               .append(", status=")
               .append(status)
               .append(", currency=")
               .append(currency)
               .append(", amount=")
               .append(amount)
               .append(", update=")
               .append(update)
               .append(", description=")
               .append(description)
               .append("]");
        return builder.toString();
    }
}
