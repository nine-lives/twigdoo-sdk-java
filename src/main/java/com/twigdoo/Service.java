package com.twigdoo;

import org.joda.time.LocalDate;

public class Service {
    private String name;
    private String address;
    private Integer budget;
    private String currency;
    private LocalDate date;

    public String getName() {
        return name;
    }

    public Service withName(String name) {
        this.name = name;
        return this;

    }

    public String getAddress() {
        return address;
    }

    public Service withAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getBudget() {
        return budget;
    }

    public Service withBudget(Integer budget) {
        this.budget = budget;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Service withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Service withDate(LocalDate date) {
        this.date = date;
        return this;
    }
}
