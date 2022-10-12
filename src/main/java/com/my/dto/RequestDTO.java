package com.my.dto;

public class RequestDTO {
    private int accountId;
    private String status;
    private String accountName;
    private int paymentCount;

    public static class Builder {
        private RequestDTO newRequest;

        public Builder() {
            newRequest = new RequestDTO();
        }

        public RequestDTO.Builder accountId(int accountId) {
            newRequest.accountId = accountId;
            return this;
        }

        public RequestDTO.Builder status(String status) {
            newRequest.status = status;
            return this;
        }

        public RequestDTO.Builder accountName(String accountName) {
            newRequest.accountName = accountName;
            return this;
        }

        public RequestDTO.Builder paymentCount(int paymentCount) {
            newRequest.paymentCount = paymentCount;
            return this;
        }

        public RequestDTO build() {
            return newRequest;
        }
    }


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(int paymentCount) {
        this.paymentCount = paymentCount;
    }
}
