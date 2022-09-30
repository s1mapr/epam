package com.my.dao;

import java.sql.*;
import java.util.Random;

public class CardDAO {

    private static final String GET_CARD_BY_NUMBER = "SELECT * FROM card WHERE number = ?";
    private static final String CREATE_NEW_CARD = "INSERT INTO card(number, expiration_date, cvv, amount) VALUES (?, ?, ?, ?)";




    public static int createNewCard(String number, String date, String cvv){
        int cardId = -1;
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_NEW_CARD, Statement.RETURN_GENERATED_KEYS)) {
            int min = 100;
            int max = 1000;
            int diff = max - min;
            Random random = new Random();
            int amount = random.nextInt(diff + 1) + min;
            statement.setString(1, number);
            statement.setString(2, date);
            statement.setString(3, cvv);
            statement.setInt(4, amount);
            statement.executeUpdate();
            try(ResultSet rs = statement.getGeneratedKeys()){
                if(rs.next()){
                    cardId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardId;
    }

    public static boolean checkCardNumber(String number) {
        String cardNumber = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CARD_BY_NUMBER)) {
            statement.setString(1, number);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    cardNumber = rs.getString("number");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardNumber == null;
    }


}

