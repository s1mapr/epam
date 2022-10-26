package com.my.dao;

import com.my.dto.RequestDTO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    private static final Logger log = Logger.getLogger(RequestDAO.class);
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
            log.info("select all request");
        } catch (SQLException e) {
            log.error("problem with selecting all request");
            log.error("Exception -  " + e);
        }
        return requestList;
    }

    public static void acceptRequest(int accountId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_STATUS);
            connection.setAutoCommit(false);
            statement.setInt(1, accountId);
            statement.executeUpdate();
            connection.commit();
            log.info("accept request where account id = " + accountId);
        } catch (SQLException e) {
            log.error("problem with accepting request where account id = " + accountId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    public static void createNewRequest(int accountId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_NEW_REQUEST);
            connection.setAutoCommit(false);
            statement.setInt(1, accountId);
            statement.executeUpdate();
            connection.commit();
            log.info("create new request for account with id: " + accountId);
        } catch (SQLException e) {
            log.error("problem with creating new request for account with id: " + accountId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    public static int getCountOfRequest() {
        int count = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_COUNT_OF_REQUEST)) {
            rs.next();
            count = rs.getInt("count");
            log.info("select count of all request");
        } catch (SQLException e) {
            log.error("problem with selecting count of all request");
            log.error("Exception -  " + e);
        }
        return count;
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
