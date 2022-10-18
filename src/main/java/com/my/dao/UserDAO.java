package com.my.dao;

import com.my.entities.Account;
import com.my.entities.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO {
    private static final Logger log = Logger.getLogger(UserDAO.class);
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user JOIN role ON role.id = user.role_id WHERE login = ? AND password = ?";
    private static final String CREATE_NEW_USER = "INSERT INTO user (login, password, first_name, last_name, email, phone_number, role_id, status) VALUES (?, ?, ?, ?, ?, ?, 1, \"unblocked\");";
    private static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String BLOCK_USER = "UPDATE user SET status = \"blocked\" WHERE id = ?";
    private static final String UNBLOCK_USER = "UPDATE user SET status = \"unblocked\" WHERE id = ?";
    private static final String GET_ALL_USERS_COUNT = "SELECT COUNT(id) AS count FROM user WHERE role_id = '1'";
    private static final String UPDATE_USER_DATA = "UPDATE user SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE id = ?";
    private static final String SET_USER_AVATAR = "UPDATE user SET avatar_url = ? WHERE id = ?";

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
                String avatarURL = rs.getString("avatar_url");
                String userStatus = rs.getString("status");
                if (Objects.isNull(avatarURL)) {
                    avatarURL = "\\epamProject\\upload\\default.png";
                } else {
                    avatarURL = avatarURL.split("target")[1];
                }
                int accountsCount = AccountDAO.getCountOfUsersAccounts(userId);
                int paymentsCount = ReceiptDAO.getPaymentsCountOfUser(userId);
                user = new User.Builder()
                        .id(userId)
                        .login(userLogin)
                        .password(userPassword)
                        .firstName(userFirstName)
                        .lastName(userLastName)
                        .email(userEmail)
                        .phoneNumber(userPhoneNumber)
                        .role(userRole)
                        .accountsCount(accountsCount)
                        .paymentsCount(paymentsCount)
                        .avatarURL(avatarURL)
                        .status(userStatus)
                        .build();
            }
            log.info("select user with login " + login);
        } catch (SQLException e) {
            log.error("problem with selecting user with login " + login);
            log.error("Exception -  " + e);
        }
        return user;
    }

    public static void registration(String login, String password, String firstName, String lastName, String email, String phoneNumber) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_NEW_USER);
            connection.setAutoCommit(false);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, email);
            statement.setString(6, phoneNumber);
            statement.executeUpdate();
            connection.commit();
            log.info("creat new user with login: " + login);
        } catch (SQLException e) {
            log.error("problem with creating new user with login: " + login);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
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
            log.info("check user for registration where login = " + login);
        } catch (SQLException e) {
            log.error("problem with checking user for registration where login = " + login);
            log.error("Exception -  " + e);
        }
        return userLogin == null;
    }

    public static int getAllUsersCount() {
        int count = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_USERS_COUNT)) {
            rs.next();
            count = rs.getInt("count");
            log.info("get count of all users");
        } catch (SQLException e) {
            log.error("problem with getting count of all users");
            log.error("Exception -  " + e);
        }
        return count;
    }

    public static void blockUser(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(BLOCK_USER);
            connection.setAutoCommit(false);
            statement.setInt(1, userId);
            statement.executeUpdate();
            connection.commit();
            log.info("block user with id: " + userId);
        } catch (SQLException e) {
            log.error("problem with blocking user with id: " + userId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    public static void unblockUser(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(UNBLOCK_USER);
            connection.setAutoCommit(false);
            statement.setInt(1, userId);
            statement.executeUpdate();
            connection.commit();
            log.info("unblock user with id: " + userId);
        } catch (SQLException e) {
            log.error("problem with unblocking user with id: " + userId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }


    public static List<User> usersPagination(int currentPage, String query) {
        List<User> list = new ArrayList<>();
        User user = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, (currentPage - 1) * 10);
            try (ResultSet rs = statement.executeQuery()) {
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
            log.info("get users");
        } catch (SQLException e) {
            log.error("problem with getting users");
            log.error("Exception -  " + e);
        }
        return list;
    }

    public static void updateUserData(String firstName, String lastName, String email, String phoneNumber, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_USER_DATA);
            connection.setAutoCommit(false);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);
            statement.setInt(5, userId);
            statement.executeUpdate();
            connection.commit();
            log.info("update data of user with id: " + userId);
        } catch (SQLException e) {
            log.error("problem with updating data of user with id: " + userId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    public static void setNewAvatar(String url, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(SET_USER_AVATAR);
            connection.setAutoCommit(false);
            statement.setString(1, url);
            statement.setInt(2, userId);
            statement.executeUpdate();
            connection.commit();
            log.info("update avatar of user with id: " + userId);
        } catch (SQLException e) {
            log.error("problem with updating avatar of user with id: " + userId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    private static void close(PreparedStatement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void rollBack(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}