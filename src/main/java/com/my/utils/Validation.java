package com.my.utils;

public class Validation {
    private boolean login;
    private boolean password;
    private boolean firstName;
    private boolean lastName;
    private boolean email;
    private boolean phoneNumber;
    private boolean paymentAmount;
    private boolean cardNumber;
    private boolean paymentPurpose;
    private boolean patronymic;
    private boolean fineNumber;
    private boolean accountName;
    private boolean expirationDate;
    private boolean cvv;
    private boolean meterW;
    private boolean meterE;
    private boolean meterG;
    private boolean amountW;
    private boolean amountE;
    private boolean amountG;

    public boolean registrationValidation(String login, String password, String firstName,
                                          String lastName, String email, String phoneNumber) {
        this.login = checkLogin(login);
        this.password = checkPassword(password);
        this.firstName = checkFirstName(firstName);
        this.lastName = checkLastName(lastName);
        this.email = checkEmail(email);
        this.phoneNumber = checkPhoneNumber(phoneNumber);
        return this.login && this.password && this.firstName && this.lastName && this.email && this.phoneNumber;
    }

    public boolean phoneRechargeValidation(String phoneNumber, String paymentAmount) {
        this.phoneNumber = checkPhoneNumber(phoneNumber);
        this.paymentAmount = checkPaymentAmount(paymentAmount);
        return this.phoneNumber && this.paymentAmount;
    }

    public boolean cardTransferValidation(String cardNumber, String firstName, String lastName, String paymentAmount) {
        this.cardNumber = checkCardNumber(cardNumber);
        this.firstName = checkFirstName(firstName);
        this.lastName = checkLastName(lastName);
        this.paymentAmount = checkPaymentAmount(paymentAmount);
        return this.cardNumber && this.firstName && this.lastName && this.paymentAmount;
    }

    public boolean servicesPaymentValidation(String cardNumber, String paymentPurpose, String paymentAmount) {
        this.cardNumber = checkCardNumber(cardNumber);
        this.paymentPurpose = checkPaymentPurpose(paymentPurpose);
        this.paymentAmount = checkPaymentAmount(paymentAmount);
        return this.cardNumber && this.paymentPurpose && this.paymentAmount;
    }

    public boolean finesPaymentValidation(String firstName, String patronymic, String lastName, String fineNumber, String paymentAmount) {
        this.firstName = checkFirstName(firstName);
        this.patronymic = checkPatronymic(patronymic);
        this.lastName = checkLastName(lastName);
        this.fineNumber = checkFineNumber(fineNumber);
        this.paymentAmount = checkPaymentAmount(paymentAmount);
        return this.firstName && this.patronymic && this.lastName && this.fineNumber && this.paymentAmount;
    }

    public boolean newCardValidation(String accountName, String cardNumber, String expirationDate, String cvv) {
        this.accountName = checkAccountName(accountName);
        this.cardNumber = checkCardNumber(cardNumber);
        this.expirationDate = checkExpirationDate(expirationDate);
        this.cvv = checkCVV(cvv);
        return this.accountName && this.cardNumber && this.expirationDate && this.cvv;
    }

    public boolean utilitiesPaymentValidation(String meterW, String meterE, String meterG,
                                              String amountW, String amountE, String amountG) {
        this.meterW = checkMeter(meterW);
        this.meterE = checkMeter(meterE);
        this.meterG = checkMeter(meterG);
        this.amountW = checkPaymentAmount(amountW);
        this.amountE = checkPaymentAmount(amountE);
        this.amountG = checkPaymentAmount(amountG);
        return this.meterW && this.meterE && this.meterG && this.amountW && this.amountE && this.amountG;
    }

    private static boolean checkLogin(String login) {
        return login.matches("^[a-zA-Z0-9._-]{3,18}$");
    }

    private static boolean checkPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
    }

    private static boolean checkFirstName(String firstName) {
        return firstName.matches("^[a-zA-ZА-Яа-яЇїіІ]{2,15}");
    }

    private static boolean checkPatronymic(String patronymic) {
        return patronymic.matches("^[a-zA-ZА-Яа-яЇїіІ]{6,25}");
    }

    private static boolean checkLastName(String lastName) {
        return lastName.matches("^[a-zA-ZА-Яа-яЇїіІ]{3,25}");
    }

    private static boolean checkEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private static boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\+380\\d{9}") || phoneNumber.matches("0\\d{9}$");
    }

    private static boolean checkPaymentAmount(String paymentAmount) {
        return paymentAmount.matches("^[0-9]*[.]?[0-9]{1,2}+$");
    }

    private static boolean checkCardNumber(String cardNumber) {
        return cardNumber.matches("^[0-9]{16}$");
    }

    private static boolean checkPaymentPurpose(String paymentPurpose) {
        return paymentPurpose.matches("^[a-zA-ZА-Яа-яЇїіІ]{5,30}");
    }

    private static boolean checkFineNumber(String fineNumber) {
        return fineNumber.matches("^[a-zA-Z0-9._-]{3,25}$");
    }

    private static boolean checkAccountName(String accountName) {
        return accountName.matches("^[a-zA-Z0-9._-]{5,30}$");
    }

    private static boolean checkExpirationDate(String expirationDate) {
        return expirationDate.matches("^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$");
    }

    private static boolean checkCVV(String cvv) {
        return cvv.matches("^[0-9]{3,4}$");
    }

    private static boolean checkMeter(String meter) {
        return meter.matches("^[0-9]{1,5}+$");
    }


    public boolean isLogin() {
        return login;
    }

    public boolean isPassword() {
        return password;
    }

    public boolean isFirstName() {
        return firstName;
    }

    public boolean isLastName() {
        return lastName;
    }

    public boolean isEmail() {
        return email;
    }

    public boolean isPhoneNumber() {
        return phoneNumber;
    }

    public boolean isPaymentAmount() {
        return paymentAmount;
    }

    public boolean isCardNumber() {
        return cardNumber;
    }

    public boolean isPaymentPurpose() {
        return paymentPurpose;
    }

    public boolean isPatronymic() {
        return patronymic;
    }

    public boolean isFineNumber() {
        return fineNumber;
    }

    public boolean isAccountName() {
        return accountName;
    }

    public boolean isExpirationDate() {
        return expirationDate;
    }

    public boolean isCvv() {
        return cvv;
    }

    public boolean isMeterW() {
        return meterW;
    }

    public boolean isMeterE() {
        return meterE;
    }

    public boolean isMeterG() {
        return meterG;
    }

    public boolean isAmountW() {
        return amountW;
    }

    public boolean isAmountE() {
        return amountE;
    }

    public boolean isAmountG() {
        return amountG;
    }
}