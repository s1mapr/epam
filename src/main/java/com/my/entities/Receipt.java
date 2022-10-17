package com.my.entities;

import java.util.Date;

public class Receipt {
    private int id;
    private String name;
    private Date date;
    private String status;
    private String accountName;
    private String purpose;
    private Double amount;
    public static class Builder {
        private Receipt newReceipt;

        public Builder() {
            newReceipt = new Receipt();
        }

        public Receipt.Builder name(String name) {
            newReceipt.name = name;
            return this;
        }
        public Receipt.Builder id(int id) {
            newReceipt.id = id;
            return this;
        }

        public Receipt.Builder date(Date date) {
            newReceipt.date = date;
            return this;
        }

        public Receipt.Builder purpose(String purpose) {
            newReceipt.purpose = purpose;
            return this;
        }

        public Receipt.Builder accountName(String accountName) {
            newReceipt.accountName = accountName;
            return this;
        }

        public Receipt.Builder status(String status) {
            newReceipt.status = status;
            return this;
        }

        public Receipt.Builder amount(Double amount) {
            newReceipt.amount = amount;
            return this;
        }

        public Receipt build() {
            return newReceipt;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
