package com.my.entities;

public class User {

    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
    private String status;
    private int accountsCount;
    private int paymentsCount;
    private String avatarURL;

    public static class Builder{
        private User newUser;

        public Builder() {
            newUser = new User();
        }
        public Builder id(int id){
            newUser.id = id;
            return this;

        }
        public Builder login(String login){
            newUser.login = login;
            return this;
        }
        public Builder password(String password){
            newUser.password = password;
            return this;
        }
        public Builder firstName(String firstName){
            newUser.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName){
            newUser.lastName = lastName;
            return this;
        }
        public Builder email(String email){
            newUser.email = email;
            return this;
        }
        public Builder phoneNumber(String phoneNumber){
            newUser.phoneNumber = phoneNumber;
            return this;
        }
        public Builder role(String role){
            newUser.role = role;
            return this;
        }
        public Builder accountsCount(int accountsCount){
            newUser.accountsCount = accountsCount;
            return this;
        }
        public Builder paymentsCount(int paymentsCount){
            newUser.paymentsCount = paymentsCount;
            return this;
        }
        public Builder status(String status){
            newUser.status = status;
            return this;
        }
        public Builder avatarURL(String avatarURL){
            newUser.avatarURL = avatarURL;
            return this;
        }
        public User build(){
            return newUser;
        }
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountsCount() {
        return accountsCount;
    }

    public void setAccountsCount(int accountsCount) {
        this.accountsCount = accountsCount;
    }

    public int getPaymentsCount() {
        return paymentsCount;
    }

    public void setPaymentsCount(int paymentsCount) {
        this.paymentsCount = paymentsCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
