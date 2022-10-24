package com.my.dao;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Random;

public class CardDAO {
    private static final Logger log = Logger.getLogger(CardDAO.class);
    private static final String GET_CARD_BY_NUMBER = "SELECT * FROM card WHERE number = ?";
    private static final String GET_CARD_AMOUNT_BY_ID = "SELECT * FROM card WHERE id = ?";
    private static final String CREATE_NEW_CARD = "INSERT INTO card(number, expiration_date, cvv, amount) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_AMOUNT = "UPDATE card SET amount = ? WHERE id = ?";


    public static int createNewCard(String number, String date, String cvv) {
        int cardId = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_NEW_CARD, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);
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
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    cardId = rs.getInt(1);
                }
            }
            connection.commit();
            log.info("creat new card with card number: " + number);
        } catch (SQLException e) {
            log.error("problem with creating new card with card number: " + number);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
        return cardId;
    }

    public static void updateAmount(double amount, int cardId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
             connection = DBManager.getInstance().getConnection();
             statement = connection.prepareStatement(UPDATE_AMOUNT);
             connection.setAutoCommit(false);
            statement.setDouble(1, amount);
            statement.setInt(2, cardId);
            statement.executeUpdate();
            connection.commit();
            log.info("update amount of card with id: " + cardId);
        } catch (SQLException e) {
            log.error("problem with updating amount of card with id: " + cardId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    public static double getAmount(int cardId) {
        double amount = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CARD_AMOUNT_BY_ID)) {
            statement.setInt(1, cardId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                amount = rs.getDouble("amount");
            }
            log.info("get amount of card with id " + cardId);
        } catch (SQLException e) {
            log.error("problem with getting amount of card with id " + cardId);
            log.error("Exception -  " + e);
        }
        return amount;
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
            log.info("check card number for availability with number: " + number);
        } catch (SQLException e) {
            log.error("problem with checking card number for availability with number: " + number);
            log.error("Exception -  " + e);
        }
        return cardNumber == null;
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

