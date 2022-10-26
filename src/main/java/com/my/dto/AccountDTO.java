package com.my.dto;

public class AccountDTO {
    private String name;
    private int id;
    private Double amount;//
    private String status;
    private String userLogin;//
    private String userFirstName;//
    private String userLastName;//
    private int paymentsCount;//
    private String cardNumber;//

    public static class Builder {
        private AccountDTO newAccount;

        public Builder() {
            newAccount = new AccountDTO();
        }

        public AccountDTO.Builder name(String name) {
            newAccount.name = name;
            return this;
        }

        public AccountDTO.Builder id(int id) {
            newAccount.id = id;
            return this;
        }

        public AccountDTO.Builder amount(Double amount) {
            newAccount.amount = amount;
            return this;
        }

        public AccountDTO.Builder status(String status) {
            newAccount.status = status;
            return this;
        }

        public AccountDTO.Builder userLogin(String userLogin) {
            newAccount.userLogin = userLogin;
            return this;
        }

        public AccountDTO.Builder userFirstName(String userFirstName) {
            newAccount.userFirstName = userFirstName;
            return this;
        }

        public AccountDTO.Builder userLastName(String userLastName) {
            newAccount.userLastName = userLastName;
            return this;
        }

        public AccountDTO.Builder paymentsCount(int paymentsCount) {
            newAccount.paymentsCount = paymentsCount;
            return this;
        }

        public AccountDTO.Builder cardNumber(String cardNumber) {
            newAccount.cardNumber = cardNumber;
            return this;
        }

        public AccountDTO build() {
            return newAccount;
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public int getPaymentsCount() {
        return paymentsCount;
    }

    public void setPaymentsCount(int paymentsCount) {
        this.paymentsCount = paymentsCount;
    }
}
