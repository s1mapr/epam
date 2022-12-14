package com.my.dao;

import com.my.dto.AccountDTO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for Account entity
 */

public class AccountDAO {
    private static final Logger log = Logger.getLogger(AccountDAO.class);
    private static final String CREATE_NEW_ACCOUNT = "INSERT INTO account(card_id, name, user_id, status) VALUES (?, ?, ?, ?)";
    private static final String BLOCK_USER_ACCOUNT = "UPDATE account SET status = \"blocked\" WHERE id = ?";
    private static final String UNBLOCK_USER_ACCOUNT = "UPDATE account SET status = \"unblocked\" WHERE id = ?";
    private static final String GET_ALL_ACCOUNTS_COUNT = "SELECT COUNT(id) AS count FROM account";
    private static final String GET_CARD_ID = "SELECT * FROM account WHERE id = ?";
    private static final String GET_ACCOUNT_NAME = "SELECT * FROM account WHERE name = ? && user_id = ?";
    private static final String GET_COUNT_OF_USERS_ACCOUNTS = "SELECT COUNT(user_id) AS count FROM account WHERE user_id = ?";
    private static final String CHANGE_STATUS_TO_PENDING = "UPDATE account SET status = \"pending\" WHERE id = ?";
    /**
     * Creating new account
     *
     * @param name String
     * @param cardId int
     * @param userId int
     */
    public static void addNewAccount(String name, int cardId, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_NEW_ACCOUNT);
            connection.setAutoCommit(false);
            statement.setInt(1, cardId);
            statement.setString(2, name);
            statement.setInt(3, userId);
            statement.setString(4, "unblocked");
            statement.executeUpdate();
            connection.commit();
            log.info("add new entry in account table with account name: " + name);
        } catch (SQLException e) {
            log.error("problem with adding new entry in account table with account name: " + name);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    /**
     * Return list of AccountDTOs
     *
     * @param id int
     * @param currentPage int
     * @param query String
     * @return List of AccountDTO items. If any problems returns empty list.
     */

    public static List<AccountDTO> getUserAccounts(int id, int currentPage, String query) {
        List<AccountDTO> list = new ArrayList<>();
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, (currentPage - 1) * 8);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int accountId = rs.getInt("id");
                    String name = rs.getString("name");
                    String cardNumber = rs.getString("card.number");
                    Double amount = rs.getDouble("card.amount");
                    String status = rs.getString("status");
                    AccountDTO account = new AccountDTO.Builder()
                            .id(accountId)
                            .name(name)
                            .cardNumber(cardNumber)
                            .amount(amount)
                            .status(status)
                            .build();
                    list.add(account);
                }
            }
            log.info("select user accounts(with pagination) where user id = " + id);
        } catch (SQLException e) {
            log.error("problem with selecting user accounts(with pagination) where user id = " + id);
            log.error("Exception -  " + e);
        }
        return list;
    }

    /**
     * Return list of AccountDTOs
     *
     * @param id int
     * @return List of AccountDTO items. If any problems returns empty list.
     */

    public static List<AccountDTO> getUserAccountsWithoutPagination(int id) {
        List<AccountDTO> list = new ArrayList<>();
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int accountId = rs.getInt("id");
                    String name = rs.getString("name");
                    String cardNumber = rs.getString("card.number");
                    Double amount = rs.getDouble("card.amount");
                    String status = rs.getString("status");
                    AccountDTO account = new AccountDTO.Builder()
                            .id(accountId)
                            .name(name)
                            .cardNumber(cardNumber)
                            .amount(amount)
                            .status(status)
                            .build();
                    list.add(account);
                }
            }
            log.info("select user accounts(without pagination) where user id = " + id);
        } catch (SQLException e) {
            log.error("problem with selecting user accounts(without pagination) where user id = " + id);
            log.error("Exception -  " + e);
        }
        return list;
    }

    /**
     * Update account status by Account identifier
     *
     * @param accountId int
     */

    public static void blockAccount(int accountId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(BLOCK_USER_ACCOUNT);
            connection.setAutoCommit(false);
            statement.setInt(1, accountId);
            statement.executeUpdate();
            connection.commit();
            log.info("block account with id: " + accountId);
        } catch (SQLException e) {
            log.error("problem with blocking account with id: " + accountId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    /**
     * Update account status by Account identifier
     *
     * @param accountId int
     */

    public static void unblockAccount(int accountId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(UNBLOCK_USER_ACCOUNT);
            connection.setAutoCommit(false);
            statement.setInt(1, accountId);
            statement.executeUpdate();
            connection.commit();
            log.info("unblock account with id: " + accountId);
        } catch (SQLException e) {
            log.error("problem with unblocking account with id: " + accountId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    /**
     * Update account status by Account identifier
     *
     * @param accountId int
     */

    public static void setPendingStatus(int accountId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CHANGE_STATUS_TO_PENDING);
            connection.setAutoCommit(false);
            statement.setInt(1, accountId);
            statement.executeUpdate();
            connection.commit();
            log.info("set pending status to account with id: " + accountId);
        } catch (SQLException e) {
            log.error("problem with setting pending status to account with id: " + accountId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    /**
     * Return count of all accounts
     */

    public static int getAllAccountsCount() {
        int count = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_ACCOUNTS_COUNT)) {
            rs.next();
            count = rs.getInt("count");
            log.info("select count of all accounts");
        } catch (SQLException e) {
            log.error("problem with selecting of count of all accounts");
            log.error("Exception -  " + e);
        }
        return count;
    }

    /**
     * Return id of card by accountId
     *
     * @param accountId int
     */


    public static int getCardId(int accountId) {
        int cardId = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CARD_ID)) {
            statement.setInt(1, accountId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                cardId = rs.getInt("card_id");
            }
            log.info("select card number of account with id: " + accountId);
        } catch (SQLException e) {
            log.error("problem with selecting of card number of account with id: " + accountId);
            log.error("Exception -  " + e);
        }
        return cardId;
    }

    /**
     * Return count of users accounts by userId
     *
     * @param userId int
     */



    public static int getCountOfUsersAccounts(int userId) {
        int count = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_COUNT_OF_USERS_ACCOUNTS)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                count = rs.getInt("count");
            }
            log.info("select accounts count of user with id: " + userId);
        } catch (SQLException e) {
            log.error("problem with selecting of accounts count of user with id: " + userId);
            log.error("Exception -  " + e);
        }
        return count;
    }

    /**
     * Return list of AccountDTOs
     *
     * @param currentPage int
     * @param query String
     * @return List of AccountDTO items. If any problems returns empty list.
     */


    public static List<AccountDTO> accountPagination(int currentPage, String query) {
        List<AccountDTO> accounts = new ArrayList<>();
        AccountDTO account = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, (currentPage - 1) * 10);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    String userLogin = rs.getString("user.login");
                    String userFirstName = rs.getString("user.first_name");
                    String userLastName = rs.getString("user.last_name");
                    int count = ReceiptDAO.getPaymentCountInAccount(id);
                    account = new AccountDTO.Builder()
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
            log.info("select accounts");
        } catch (SQLException e) {
            log.error("problem with selecting accounts");
            log.error("Exception -  " + e);
        }
        return accounts;
    }

    /**
     * Checks database for account with accountName for existence
     *
     * @param accountName String
     * @param userId int
     */


    public static boolean checkAccountName(String accountName, int userId){
        boolean checker = false;
        try(Connection connection = DBManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_NAME)) {
            statement.setString(1, accountName);
            statement.setInt(2, userId);
            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()) {
                    checker = true;
                }
            }
            log.info("check account name for availability with name: " + accountName);
        } catch (SQLException e) {
            log.error("problem with checking account name for availability with name: " + accountName);
            log.error("Exception -  " + e);
        }
        return checker;
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


