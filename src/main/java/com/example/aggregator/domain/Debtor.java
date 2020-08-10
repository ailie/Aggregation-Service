package com.example.aggregator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/** Intentional spelling mistake - to be consistent with the remote API. */
@Entity
public class Debtor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String maskedPan;
    private String name;

    Debtor() {
    }

    public Debtor(long id, String maskedPan, String name) {
        this.id = id;
        this.maskedPan = maskedPan;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Debtor [id=")
               .append(id)
               .append(", maskedPan=")
               .append(maskedPan)
               .append(", name=")
               .append(name)
               .append("]");
        return builder.toString();
    }
}
