package com.my.dao;

import com.my.entities.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String CREATE_NEW_ACCOUNT = "INSERT INTO account(card_id, name, user_id, status) VALUES (?, ?, ?, ?)";
    private static final String GET_USER_ACCOUNTS = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ?";
    private static final String BLOCK_USER_ACCOUNT = "UPDATE account SET status = \"blocked\" WHERE id = ?";
    private static final String UNBLOCK_USER_ACCOUNT = "UPDATE account SET status = \"unblocked\" WHERE id = ?";
    private static final String GET_ALL_ACCOUNTS = "SELECT * FROM account";
    private static final String GET_CARD_ID = "SELECT * FROM account WHERE id = ?";


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

    public static List<Account> getUserAccounts(int id) {
        List<Account> list = new ArrayList<>();
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_ACCOUNTS)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int accountId = rs.getInt("id");
                    String name = rs.getString("name");
                    Double amount = rs.getDouble("card.amount");
                    String status = rs.getString("status");
                    Account account = new Account.Builder()
                            .id(accountId)
                            .name(name)
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

    public static List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        Account account = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_ACCOUNTS)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String status = rs.getString("status");
                account = new Account.Builder()
                        .id(id)
                        .name(name)
                        .status(status)
                        .build();
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
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

}
