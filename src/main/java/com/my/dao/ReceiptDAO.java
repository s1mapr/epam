package com.my.dao;

import com.my.entities.Receipt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDAO {
    public static String CREATE_NEW_ENTRY_IN_PHONE_SERVICE = "INSERT INTO phone_service(number) VALUES(?)";
    public static String CREATE_NEW_ENTRY_IN_SERV_SERVICE = "INSERT INTO serv_service(card, service) VALUES(?, ?)";
    public static String CREATE_NEW_ENTRY_IN_TRANS_SERVICE = "INSERT INTO trans_service(number, first_name, last_name) VALUES(?, ?, ?)";
    public static String CREATE_NEW_ENTRY_IN_FINES_SERVICE = "INSERT INTO fine_service(first_name, last_name, patronymic, number) VALUES(?, ?, ?, ?)";
    public static String CREATE_NEW_ENTRY_IN_UTILITIES_SERVICE = "INSERT INTO utilities_service(meter_reading_water, meter_reading_electricity, meter_reading_gas," +
            " amount_water, amount_electricity, amount_gas ) VALUES(?, ?, ?, ?, ?, ?)";
    public static String CREATE_NEW_ENTRY_IN_RECEIPT = "INSERT INTO receipt(name, account_id, date, status, purpose_id, amount, service_id, user_id) VALUES(?,?,?,\"prepared\", ?, ?, ?, ?)";
    public static String GET_ALL_USERS_RECEIPTS = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? LIMIT 5 OFFSET ?";
    private static final String GET_PAYMENTS_COUNT_OF_ACCOUNT_BY_ID = "SELECT COUNT(account_id) AS count FROM receipt WHERE account_id = ?";
    private static final String GET_PAYMENTS_COUNT_OF_USER_BY_ID = "SELECT COUNT(user_id) AS count FROM receipt WHERE user_id = ?";
    private static final String GET_COUNT_OF_USERS_RECEIPTS = "SELECT COUNT(user_id) AS count FROM receipt WHERE user_id = ?";

    public static int createNewEntryInPhoneService(String phoneNumber) {
        int phoneId = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_PHONE_SERVICE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, phoneNumber);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    phoneId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneId;
    }

    public static int createNewEntryInServService(String cardNumber, String serviceName) {
        int servId = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_SERV_SERVICE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cardNumber);
            statement.setString(2, serviceName);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    servId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servId;
    }

    public static int createNewEntryInTransService(String cardNumber, String firstName, String lastName) {
        int servId = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_TRANS_SERVICE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cardNumber);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    servId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servId;
    }

    public static int createNewEntryInFinesService(String firstName, String lastName, String patronymic, String number) {
        int servId = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_FINES_SERVICE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, patronymic);
            statement.setString(4, number);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    servId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servId;
    }

    public static int createNewEntryInUtilitiesService(int meterWater, int meterElectricity, int meterGas,
    double amountWater, double amountElectricity, double amountGas) {
        int servId = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_UTILITIES_SERVICE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, meterWater);
            statement.setInt(2, meterElectricity);
            statement.setInt(3, meterGas);
            statement.setDouble(4, amountWater);
            statement.setDouble(5, amountElectricity);
            statement.setDouble(6, amountGas);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    servId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servId;
    }

    public static void createEntryInReceipt(int accountId, int purposeId, double amount, int serviceId, int userId) {
        // java.sql.Date.valueOf(LocalDate.now());
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_RECEIPT)) {
            statement.setString(1, "payment_" + accountId + "_" + serviceId);
            statement.setInt(2, accountId);
            statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            statement.setInt(4, purposeId);
            statement.setDouble(5, amount);
            statement.setInt(6, serviceId);
            statement.setInt(7,userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static List<Receipt> getUsersReceiptsWithPagination(int userId, int currentPage){
        List<Receipt> receiptList = new ArrayList<>();
        Receipt receipt = null;
        try(Connection connection = DBManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS_RECEIPTS)){
            statement.setInt(1, userId);
            statement.setInt(2, (currentPage-1)*5);
            try(ResultSet rs = statement.executeQuery()){
                while(rs.next()){
                    String name = rs.getString("name");
                    Date date = rs.getDate("date");
                    String purpose = rs.getString("purpose.name");
                    String accountName = rs.getString("account.name");
                    String status = rs.getString("status");
                    Double amount = rs.getDouble("amount");
                    receipt = new Receipt.Builder()
                            .name(name)
                            .date(date)
                            .purpose(purpose)
                            .accountName(accountName)
                            .status(status)
                            .amount(amount)
                            .build();
                    receiptList.add(receipt);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return receiptList;
    }

    public static int getPaymentCountInAccount(int accountId){
        int count = -1;
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_PAYMENTS_COUNT_OF_ACCOUNT_BY_ID)) {
            statement.setInt(1,accountId);
            try(ResultSet rs = statement.executeQuery()){
                rs.next();
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public static int getPaymentsCountOfUser(int userId){
        int count = -1;
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_PAYMENTS_COUNT_OF_USER_BY_ID)) {
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

    public static int getCountOfUsersPayments(int userId){
        int count = -1;
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_COUNT_OF_USERS_RECEIPTS)) {
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


}
