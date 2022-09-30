package com.my.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAO {
    private static final String CREATE_NEW_ACCOUNT = "INSERT INTO account(card_id, name, user_id, status) VALUES (?, ?, ?, ?)";


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
}
