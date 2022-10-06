package com.my.dao;

import com.my.entities.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String CREATE_NEW_ACCOUNT = "INSERT INTO account(card_id, name, user_id, status) VALUES (?, ?, ?, ?)";
    private static final String BLOCK_USER_ACCOUNT = "UPDATE account SET status = \"blocked\" WHERE id = ?";
    private static final String UNBLOCK_USER_ACCOUNT = "UPDATE account SET status = \"unblocked\" WHERE id = ?";
    private static final String GET_ALL_ACCOUNTS_COUNT = "SELECT COUNT(id) AS count FROM account";
    private static final String GET_CARD_ID = "SELECT * FROM account WHERE id = ?";
    private static final String GET_COUNT_OF_USERS_ACCOUNTS = "SELECT COUNT(user_id) AS count FROM account WHERE user_id = ?";

    public static void addNewAccount(String name, int cardId, int userId) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ACCOUNT)) {
            statement.setInt(1, cardId);
            statement.setString(2, name);
            statement.setInt(3, userId);
            statement.setString(4, "unblocked");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Account> getUserAccounts(int id, int currentPage, String query) {
        List<Account> list = new ArrayList<>();
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, (currentPage-1)*5);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int accountId = rs.getInt("id");
                    String name = rs.getString("name");
                    String cardNumber = rs.getString("card.number");
                    Double amount = rs.getDouble("card.amount");
                    String status = rs.getString("status");
                    Account account = new Account.Builder()
                            .id(accountId)
                            .name(name)
                            .cardNumber(cardNumber)
                            .amount(amount)
                            .status(status)
                            .build();
                    list.add(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void blockAccount(int accountId) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(BLOCK_USER_ACCOUNT)) {
            statement.setInt(1, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unblockAccount(int accountId){
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UNBLOCK_USER_ACCOUNT)) {
            statement.setInt(1, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static int getAllAccountsCount() {
        int count = -1;
        try(Connection connection = DBManager.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(GET_ALL_ACCOUNTS_COUNT)) {
            rs.next();
            count = rs.getInt("count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public static int getCardId(int accountId){
        int cardId = -1;
        try(Connection connection = DBManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_CARD_ID)){
            statement.setInt(1, accountId);
            try(ResultSet rs = statement.executeQuery()){
                rs.next();
                cardId = rs.getInt("card_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardId;
    }

    public static int getCountOfUsersAccounts(int userId){
        int count = -1;
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_COUNT_OF_USERS_ACCOUNTS)) {
            statement.setInt(1,userId);
            try(ResultSet rs = statement.executeQuery()){
                rs.next();
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public static List<Account> accountPagination(int currentPage, String query){
        List<Account> accounts = new ArrayList<>();
        Account account = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, (currentPage-1)*5);
            try(ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    String userLogin = rs.getString("user.login");
                    String userFirstName = rs.getString("user.first_name");
                    String userLastName = rs.getString("user.last_name");
                    int count = ReceiptDAO.getPaymentCountInAccount(id);
                    account = new Account.Builder()
                            .id(id)
                            .name(name)
                            .status(status)
                            .userLogin(userLogin)
                            .userFirstName(userFirstName)
                            .userLastName(userLastName)
                            .paymentsCount(count)
                            .build();
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

}
