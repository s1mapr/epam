package com.my.entities;

public class Account {
    private String name;

    public static class Builder {
        private Account newAccount;

        public Builder() {
            newAccount = new Account();
        }

        public Account.Builder name(String name) {
            newAccount.name = name;
            return this;
        }
        public Account build() {
            return newAccount;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
