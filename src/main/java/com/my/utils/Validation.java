package com.my.utils;

import com.my.entities.Receipt;

import java.util.Date;

public class Validation {
    private boolean login;
    private boolean password;
    private boolean firstName;
    private boolean lastName;
    private boolean email;
    private boolean phoneNumber;


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


    private static boolean checkLogin(String login) {
        return login.matches("^[a-zA-Z0-9._-]{3,}$");
    }

    private static boolean checkPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
    }

    private static boolean checkFirstName(String firstName) {
        return firstName.matches("^[a-zA-ZА-Яа-яЇїіІ]{2,15}");
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
}
