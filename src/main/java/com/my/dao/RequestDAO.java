package com.my.dao;

import com.my.controllers.dto.RequestDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    private static String GET_REQUESTS_WITH_PENDING_STATUS = "SELECT * FROM request JOIN account ON account.id = request.account_id WHERE request.status = 'pending' LIMIT 10 OFFSET ?";
    private static String UPDATE_STATUS = "UPDATE request SET status = 'accepted' WHERE account_id = ?";
    private static String CREATE_NEW_REQUEST = "INSERT INTO request(account_id, status) VALUES (?, 'pending')";
    private static String GET_COUNT_OF_REQUEST = "SELECT COUNT(id) AS count FROM request WHERE status = 'pending'";

    public static List<RequestDTO> getRequests(int pageNumber) {
        List<RequestDTO> requestList = new ArrayList<>();
        RequestDTO request;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_REQUESTS_WITH_PENDING_STATUS)) {
            statement.setInt(1, (pageNumber - 1) * 10);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String status = rs.getString("request.status");
                    int accountId = rs.getInt("account.id");
                    String accountName = rs.getString("account.name");
                    int paymentCount = ReceiptDAO.getPaymentCountInAccount(accountId);
                    request = new RequestDTO.Builder()
                            .accountId(accountId)
                            .accountName(accountName)
                            .status(status)
                            .paymentCount(paymentCount)
                            .build();
                    requestList.add(request);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return requestList;
    }

    public static void acceptRequest(int accountId) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS);) {
            statement.setInt(1, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createNewRequest(int accountId) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NEW_REQUEST)) {
            statement.setInt(1, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getCountOfRequest() {
        int count = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_COUNT_OF_REQUEST)) {
            rs.next();
            count = rs.getInt("count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }


}
