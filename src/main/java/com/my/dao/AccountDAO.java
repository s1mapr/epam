package com.my.dao;

import com.my.entities.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String CREATE_NEW_ACCOUNT = "INSERT INTO account(card_id, name, user_id, status) VALUES (?, ?, ?, ?)";
    private static final String GET_USER_ACCOUNTS = "SELECT * FROM account WHERE user_id = ?";

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

    public static List<Account> getUserAccounts(int id){
        List<Account> list = new ArrayList<>();
        try(Connection connection = DBManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_USER_ACCOUNTS)){
            statement.setInt(1, id);
            try(ResultSet rs = statement.executeQuery()){
                while(rs.next()){
                    int accountId = rs.getInt("id");
                    String name = rs.getString("name");
                    Account account = new Account.Builder()
                            .id(accountId)
                            .name(name)
                            .build();
                    list.add(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
