package com.my.dao;

import com.my.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user JOIN role ON role.id = user.role_id WHERE login = ? AND password = ?";
    private static final String CREATE_NEW_USER = "INSERT INTO user (login, password, first_name, last_name, email, phone_number, role_id, status) VALUES (?, ?, ?, ?, ?, ?, 1, \"unblocked\");";
    private static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String BLOCK_USER = "UPDATE user SET status = \"blocked\" WHERE id = ?";
    private static final String UNBLOCK_USER = "UPDATE user SET status = \"unblocked\" WHERE id = ?";
    private static final String GET_ALL_USERS_COUNT = "SELECT COUNT(id) AS count FROM user WHERE role_id = '1'";
    private static final String UPDATE_USER_DATA = "UPDATE user SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE id = ?";
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

    public static int getAllUsersCount() {
        int count = -1;
        try(Connection connection = DBManager.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_USERS_COUNT)) {
            rs.next();
            count = rs.getInt("count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public static void blockUser(int userId) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(BLOCK_USER)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unblockUser(int userId){
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UNBLOCK_USER)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<User> usersPagination(int currentPage, String query){
        List<User> list = new ArrayList<>();
        User user = null;
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, (currentPage-1)*5);
            try(ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int paymentsCount = ReceiptDAO.getPaymentsCountOfUser(id);
                    String login = rs.getString("login");
                    String firsName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phone_number");
                    String status = rs.getString("status");
                    user = new User.Builder()
                            .id(id)
                            .login(login)
                            .firstName(firsName)
                            .lastName(lastName)
                            .email(email)
                            .phoneNumber(phoneNumber)
                            .status(status)
                            .paymentsCount(paymentsCount)
                            .build();
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void updateUserData(String firstName, String lastName, String email, String phoneNumber, int userId){
        try(Connection connection = DBManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_USER_DATA)){
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, email);
        statement.setString(4, phoneNumber);
        statement.setInt(5, userId);
        statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}