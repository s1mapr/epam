package com.my.entities;

import java.util.Date;

public class Account {
    private String name;
    private int id;
    private Double amount;
    private String status;
    public static class Builder {
        private Account newAccount;

        public Builder() {
            newAccount = new Account();
        }

        public Account.Builder name(String name) {
            newAccount.name = name;
            return this;
        }
        public Account.Builder id(int id) {
            newAccount.id = id;
            return this;
        }
        public Account.Builder amount(Double amount) {
            newAccount.amount = amount;
            return this;
        }
        public Account.Builder status(String status) {
            newAccount.status = status;
            return this;
        }
        public Account build() {
            return newAccount;
        }
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
