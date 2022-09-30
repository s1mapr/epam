package com.my.dao;

import com.my.entities.User;

import java.sql.*;
import java.util.Random;

public class UserDAO {
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user JOIN role ON role.id = user.role_id WHERE login = ? AND password = ?";
    private static final String CREATE_NEW_USER = "INSERT INTO user (login, password, first_name, last_name, email, phone_number, role_id, status) VALUES (?, ?, ?, ?, ?, ?, 1, \"unblocked\");";
    private static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";

    public static User getUserByLoginAndPassword(String login, String password) {
        User user = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                int userId = rs.getInt("id");
                String userLogin = rs.getString("login");
                String userPassword = rs.getString("password");
                String userFirstName = rs.getString("first_name");
                String userLastName = rs.getString("last_name");
                String userEmail = rs.getString("email");
                String userPhoneNumber = rs.getString("phone_number");
                String userRole = rs.getString("name");
                user = new User.Builder()
                        .id(userId)
                        .login(userLogin)
                        .password(userPassword)
                        .firstName(userFirstName)
                        .lastName(userLastName)
                        .email(userEmail)
                        .phoneNumber(userPhoneNumber)
                        .role(userRole)
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void registration(String login, String password, String firstName, String lastName, String email, String phoneNumber) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NEW_USER)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, email);
            statement.setString(6, phoneNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkUserForRegistration(String login) {
        String userLogin = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    userLogin = rs.getString("login");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userLogin == null;
    }
}