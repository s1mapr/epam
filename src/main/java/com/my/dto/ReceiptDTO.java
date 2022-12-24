package com.my.dto;

import java.util.Date;

/**
 * Describes Receipt's DTO
 */

public class ReceiptDTO {
    private int id;
    private String accountName;
    private String paymentName;
    private Date paymentDate;
    private String userCard;
    private String userFirstName;
    private String userLastName;
    private String phoneNumber;
    private double amount;
    private String paymentCardNumber;
    private String purpose;
    private String paymentStatus;
    private String paymentFirstName;
    private String paymentLastName;
    private String paymentPatronymic;
    private String fineNumber;
    private String meterW;
    private String meterE;
    private String meterG;
    private double amountW;
    private double amountE;
    private double amountG;

    public static class Builder {
        private ReceiptDTO newReceipt;

        public Builder() {
            newReceipt = new ReceiptDTO();
        }

        public ReceiptDTO.Builder paymentName(String paymentName) {
            newReceipt.paymentName = paymentName;
            return this;
        }

        public ReceiptDTO.Builder paymentDate(Date paymentDate) {
            newReceipt.paymentDate = paymentDate;
            return this;
        }

        public ReceiptDTO.Builder accountName(String accountName) {
            newReceipt.accountName = accountName;
            return this;
        }

        public ReceiptDTO.Builder id(int id) {
            newReceipt.id = id;
            return this;
        }

        public ReceiptDTO.Builder userCard(String userCard) {
            newReceipt.userCard = userCard;
            return this;
        }

        public ReceiptDTO.Builder userFirstName(String userFirstName) {
            newReceipt.userFirstName = userFirstName;
            return this;
        }

        public ReceiptDTO.Builder userLastName(String userLastName) {
            newReceipt.userLastName = userLastName;
            return this;
        }

        public ReceiptDTO.Builder phoneNumber(String phoneNumber) {
            newReceipt.phoneNumber = phoneNumber;
            return this;
        }

        public ReceiptDTO.Builder amount(double amount) {
            newReceipt.amount = amount;
            return this;
        }

        public ReceiptDTO.Builder paymentCardNumber(String paymentCardNumber) {
            newReceipt.paymentCardNumber = paymentCardNumber;
            return this;
        }

        public ReceiptDTO.Builder purpose(String purpose) {
            newReceipt.purpose = purpose;
            return this;
        }

        public ReceiptDTO.Builder paymentStatus(String paymentStatus) {
            newReceipt.paymentStatus = paymentStatus;
            return this;
        }

        public ReceiptDTO.Builder paymentFirstName(String paymentFirstName) {
            newReceipt.paymentFirstName = paymentFirstName;
            return this;
        }

        public ReceiptDTO.Builder paymentLastName(String paymentLastName) {
            newReceipt.paymentLastName = paymentLastName;
            return this;
        }

        public ReceiptDTO.Builder paymentPatronymic(String paymentPatronymic) {
            newReceipt.paymentPatronymic = paymentPatronymic;
            return this;
        }

        public ReceiptDTO.Builder fineNumber(String fineNumber) {
            newReceipt.fineNumber = fineNumber;
            return this;
        }

        public ReceiptDTO.Builder meterW(String meterW) {
            newReceipt.meterW = meterW;
            return this;
        }

        public ReceiptDTO.Builder meterE(String meterE) {
            newReceipt.meterE = meterE;
            return this;
        }

        public ReceiptDTO.Builder meterG(String meterG) {
            newReceipt.meterG = meterG;
            return this;
        }

        public ReceiptDTO.Builder amountW(double amountW) {
            newReceipt.amountW = amountW;
            return this;
        }

        public ReceiptDTO.Builder amountE(double amountE) {
            newReceipt.amountE = amountE;
            return this;
        }

        public ReceiptDTO.Builder amountG(double amountG) {
            newReceipt.amountG = amountG;
            return this;
        }

        public ReceiptDTO build() {
            return newReceipt;
        }
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    public void setPaymentCardNumber(String paymentCardNumber) {
        this.paymentCardNumber = paymentCardNumber;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentFirstName() {
        return paymentFirstName;
    }

    public void setPaymentFirstName(String paymentFirstName) {
        this.paymentFirstName = paymentFirstName;
    }

    public String getPaymentLastName() {
        return paymentLastName;
    }

    public void setPaymentLastName(String paymentLastName) {
        this.paymentLastName = paymentLastName;
    }

    public String getPaymentPatronymic() {
        return paymentPatronymic;
    }

    public void setPaymentPatronymic(String paymentPatronymic) {
        this.paymentPatronymic = paymentPatronymic;
    }

    public String getFineNumber() {
        return fineNumber;
    }

    public void setFineNumber(String fineNumber) {
        this.fineNumber = fineNumber;
    }

    public String getMeterW() {
        return meterW;
    }

    public void setMeterW(String meterW) {
        this.meterW = meterW;
    }

    public String getMeterE() {
        return meterE;
    }

    public void setMeterE(String meterE) {
        this.meterE = meterE;
    }

    public String getMeterG() {
        return meterG;
    }

    public void setMeterG(String meterG) {
        this.meterG = meterG;
    }

    public double getAmountW() {
        return amountW;
    }

    public void setAmountW(double amountW) {
        this.amountW = amountW;
    }

    public double getAmountE() {
        return amountE;
    }

    public void setAmountE(double amountE) {
        this.amountE = amountE;
    }

    public double getAmountG() {
        return amountG;
    }

    public void setAmountG(double amountG) {
        this.amountG = amountG;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
