package com.my.entities;

public class Account {
    private String name;
    private int id;

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
        public Account build() {
            return newAccount;
        }
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
}
