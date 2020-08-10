package com.example.aggregator.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    private String     id;
    private String     userId;
    private String     update;
    private String     name;
    private String     product;
    private String     status;
    private String     type;
    private BigDecimal balance;

    Account() {
    }

    public Account(String id, String userId, String update, String name, String product,
                   String status, String type, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.update = update;
        this.name = name;
        this.product = product;
        this.status = status;
        this.type = type;
        this.balance = balance;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUpdate() {
        return update;
    }

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Account [id=")
                .append(id)
                .append(", userId=")
                .append(userId)
                .append(", update=")
                .append(update)
                .append(", name=")
                .append(name)
                .append(", product=")
                .append(product)
                .append(", status=")
                .append(status)
                .append(", type=")
                .append(type)
                .append(", balance=")
                .append(balance)
                .append("]");
        return builder.toString();
    }
}
