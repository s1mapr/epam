package com.my.dao;

import com.my.dto.ReceiptDTO;
import com.my.dto.UserDTO;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDAO {
    private static final Logger log = Logger.getLogger(ReceiptDAO.class);
    public static String CREATE_NEW_ENTRY_IN_PHONE_SERVICE = "INSERT INTO phone_service(number) VALUES(?)";
    public static String CREATE_NEW_ENTRY_IN_SERV_SERVICE = "INSERT INTO serv_service(card, service) VALUES(?, ?)";
    public static String CREATE_NEW_ENTRY_IN_TRANS_SERVICE = "INSERT INTO trans_service(number, first_name, last_name) VALUES(?, ?, ?)";
    public static String CREATE_NEW_ENTRY_IN_FINES_SERVICE = "INSERT INTO fine_service(first_name, last_name, patronymic, number) VALUES(?, ?, ?, ?)";
    public static String CREATE_NEW_ENTRY_IN_UTILITIES_SERVICE = "INSERT INTO utilities_service(meter_reading_water, meter_reading_electricity, meter_reading_gas," +
            " amount_water, amount_electricity, amount_gas ) VALUES(?, ?, ?, ?, ?, ?)";
    public static String CREATE_NEW_ENTRY_IN_RECEIPT = "INSERT INTO receipt(name, account_id, date, status, purpose_id, amount, service_id, user_id) VALUES(?,?,?,\"prepared\", ?, ?, ?, ?)";
    private static final String GET_PAYMENTS_COUNT_OF_ACCOUNT_BY_ID = "SELECT COUNT(account_id) AS count FROM receipt WHERE account_id = ?";
    private static final String GET_PAYMENTS_COUNT_OF_USER_BY_ID = "SELECT COUNT(user_id) AS count FROM receipt WHERE user_id = ?";
    private static final String GET_ALL_PAYMENTS = "SELECT * FROM receipt";
    private static final String UPDATE_STATUS_BY_ID = "UPDATE receipt SET status = \"sent\" WHERE id = ?";
    private static final String GET_DATA_FOR_PDF_PHONE = "SELECT * FROM receipt JOIN account ON account.id = receipt.account_id JOIN card ON card.id = account.card_id JOIN phone_service ON phone_service.id = receipt.service_id WHERE receipt.id = ?";
    private static final String GET_DATA_FOR_PDF_SERVICES = "SELECT * FROM receipt JOIN account ON account.id = receipt.account_id JOIN card ON card.id = account.card_id JOIN serv_service ON serv_service.id = receipt.service_id WHERE receipt.id = ?";
    private static final String GET_DATA_FOR_PDF_CARD = "SELECT * FROM receipt JOIN account ON account.id = receipt.account_id JOIN card ON card.id = account.card_id JOIN trans_service ON trans_service.id = receipt.service_id WHERE receipt.id = ?";
    private static final String GET_DATA_FOR_PDF_UTILITIES = "SELECT * FROM receipt JOIN account ON account.id = receipt.account_id JOIN card ON card.id = account.card_id JOIN utilities_service ON utilities_service.id = receipt.service_id WHERE receipt.id = ?";
    private static final String GET_DATA_FOR_PDF_FINES = "SELECT * FROM receipt JOIN account ON account.id = receipt.account_id JOIN card ON card.id = account.card_id JOIN fine_service ON fine_service.id = receipt.service_id WHERE receipt.id = ?";

    public static int createNewEntryInPhoneService(String phoneNumber) {
        int phoneId = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_PHONE_SERVICE, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);
            statement.setString(1, phoneNumber);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    phoneId = rs.getInt(1);
                }
            }
            connection.commit();
            log.info("add new entry in phone_service table with phone number: " + phoneNumber);
        } catch (SQLException e) {
            log.error("problem with adding new entry in phone_service table with phone number: " + phoneNumber);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
        return phoneId;
    }

    public static int createNewEntryInServService(String cardNumber, String serviceName) {
        int servId = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_SERV_SERVICE, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);
            statement.setString(1, cardNumber);
            statement.setString(2, serviceName);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    servId = rs.getInt(1);
                }
            }
            connection.commit();
            log.info("add new entry in serv_service table with card number: " + cardNumber);
        } catch (SQLException e) {
            log.error("problem with adding new entry in serv_service table with card number: " + cardNumber);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
        return servId;
    }

    public static int createNewEntryInTransService(String cardNumber, String firstName, String lastName) {
        int servId = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_TRANS_SERVICE, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);
            statement.setString(1, cardNumber);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    servId = rs.getInt(1);
                }
            }
            connection.commit();
            log.info("add new entry in trans_service table with card number: " + cardNumber);
        } catch (SQLException e) {
            log.error("problem with adding new entry in trans_service table with card number: " + cardNumber);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
        return servId;
    }

    public static int createNewEntryInFinesService(String firstName, String lastName, String patronymic, String number) {
        int servId = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_FINES_SERVICE, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);
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
            connection.commit();
            log.info("add new entry in fine_service table with fine number: " + number);
        } catch (SQLException e) {
            log.error("problem with adding new entry in fine_service table with fine number: " + number);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
        return servId;
    }

    public static int createNewEntryInUtilitiesService(int meterWater, int meterElectricity, int meterGas,
                                                       double amountWater, double amountElectricity, double amountGas) {
        int servId = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_UTILITIES_SERVICE, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);
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
            connection.commit();
            log.info("add new entry in utilities_service table with meters: " + meterWater + "/" + meterElectricity + "/" + meterGas);

        } catch (SQLException e) {
            log.error("problem with adding new entry in utilities_service table with meters: " + meterWater + "/" + meterElectricity + "/" + meterGas);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
        return servId;
    }

    public static void createEntryInReceipt(int accountId, int purposeId, double amount, int serviceId, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_RECEIPT);
            connection.setAutoCommit(false);
            statement.setString(1, "payment_" + accountId + "_" + serviceId);
            statement.setInt(2, accountId);
            statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            statement.setInt(4, purposeId);
            statement.setDouble(5, amount);
            statement.setInt(6, serviceId);
            statement.setInt(7, userId);
            statement.executeUpdate();
            connection.commit();
            log.info("add new entry in receipt table with purpose id: " + purposeId + "; and service id: " + serviceId);
        } catch (SQLException e) {
            log.error("problem with adding new entry in receipt table with purpose id: " + purposeId + "; and service id: " + serviceId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }


    public static List<ReceiptDTO> getUsersReceiptsWithPagination(int userId, int currentPage, String query) {
        List<ReceiptDTO> receiptList = new ArrayList<>();
        ReceiptDTO receipt = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, (currentPage - 1) * 10);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("receipt.id");
                    String name = rs.getString("name");
                    Date date = rs.getDate("date");
                    String purpose = rs.getString("purpose.name");
                    String accountName = rs.getString("account.name");
                    String status = rs.getString("status");
                    double amount = rs.getDouble("amount");
                    receipt = new ReceiptDTO.Builder()
                            .paymentName(name)
                            .paymentDate(date)
                            .id(id)
                            .purpose(purpose)
                            .accountName(accountName)
                            .paymentStatus(status)
                            .amount(amount)
                            .build();
                    receiptList.add(receipt);
                }
            }
            log.info("select of user receipts");
        } catch (SQLException e) {
            log.error("problem with selecting of user receipts");
            log.error("Exception -  " + e);
        }
        return receiptList;
    }

    public static int getPaymentCountInAccount(int accountId) {
        int count = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PAYMENTS_COUNT_OF_ACCOUNT_BY_ID)) {
            statement.setInt(1, accountId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                count = rs.getInt("count");
            }
            log.info("select payment count of account with id: " + accountId);
        } catch (SQLException e) {
            log.error("problem with selecting of payment count of account with id: " + accountId);
            log.error("Exception -  " + e);
        }
        return count;
    }

    public static int getPaymentsCountOfUser(int userId) {
        int count = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PAYMENTS_COUNT_OF_USER_BY_ID)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                count = rs.getInt("count");
            }
            log.info("select payment count of user with id: " + userId);
        } catch (SQLException e) {
            log.error("problem with selecting of payment count of user with id: " + userId);
            log.error("Exception -  " + e);
        }
        return count;
    }


    public static void updateStatus() {
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_PAYMENTS)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                if (date.toLocalDate().plusDays(1).getDayOfYear() <= LocalDate.now().getDayOfYear() && status.equals("prepared")) {
                    utilUpdateStatus(id);
                }
            }
            log.info("select all payments");
        } catch (SQLException e) {
            log.error("problem with selecting of all payments");
            log.error("Exception -  " + e);
        }
    }

    private static void utilUpdateStatus(int receiptId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_STATUS_BY_ID);
            statement.setInt(1, receiptId);
            statement.executeUpdate();
            connection.commit();
            log.info("update of status in receipt with id: " + receiptId);
        } catch (SQLException e) {
            log.error("problem with updating of status in receipt with id: " + receiptId);
            log.error("Exception -  " + e);
            rollBack(connection);
        } finally {
            close(statement);
            close(connection);
        }
    }

    public static ReceiptDTO getReceiptInfoPhone(int receiptId, UserDTO user) {
        ReceiptDTO receipt = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_DATA_FOR_PDF_PHONE)) {
            statement.setInt(1, receiptId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                String paymentName = rs.getString("receipt.name");
                Date paymentDate = rs.getDate("receipt.date");
                String userCard = rs.getString("card.number");
                String userFirstName = user.getFirstName();
                String userLastName = user.getLastName();
                String phoneNumber = rs.getString("phone_service.number");
                double amount = rs.getDouble("receipt.amount");
                String paymentStatus = rs.getString("receipt.status");
                receipt = new ReceiptDTO.Builder()
                        .paymentName(paymentName)
                        .paymentDate(paymentDate)
                        .userCard(userCard)
                        .userFirstName(userFirstName)
                        .userLastName(userLastName)
                        .phoneNumber(phoneNumber)
                        .amount(amount)
                        .paymentStatus(paymentStatus)
                        .build();
            }
            log.info("select receipt(phone recharge) info with id: " + receiptId);
        } catch (SQLException e) {
            log.error("problem with selecting of receipt(phone recharge) info with id: " + receiptId);
            log.error("Exception -  " + e);
        }
        return receipt;
    }

    public static ReceiptDTO getReceiptInfoService(int receiptId, UserDTO user) {
        ReceiptDTO receipt = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_DATA_FOR_PDF_SERVICES)) {
            statement.setInt(1, receiptId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                String paymentName = rs.getString("receipt.name");
                Date paymentDate = rs.getDate("receipt.date");
                String userCard = rs.getString("card.number");
                String userFirstName = user.getFirstName();
                String userLastName = user.getLastName();
                String paymentCardNumber = rs.getString("serv_service.card");
                String purpose = rs.getString("serv_service.service");
                double amount = rs.getDouble("receipt.amount");
                String paymentStatus = rs.getString("receipt.status");
                receipt = new ReceiptDTO.Builder()
                        .paymentName(paymentName)
                        .paymentDate(paymentDate)
                        .userCard(userCard)
                        .userFirstName(userFirstName)
                        .userLastName(userLastName)
                        .paymentCardNumber(paymentCardNumber)
                        .purpose(purpose)
                        .amount(amount)
                        .paymentStatus(paymentStatus)
                        .build();
            }
            log.info("select receipt(services) info with id: " + receiptId);
        } catch (SQLException e) {
            log.error("problem with selecting of receipt(services) info with id: " + receiptId);
            log.error("Exception -  " + e);
        }
        return receipt;
    }

    public static ReceiptDTO getReceiptInfoCard(int receiptId, UserDTO user) {
        ReceiptDTO receipt = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_DATA_FOR_PDF_CARD)) {
            statement.setInt(1, receiptId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                String paymentName = rs.getString("receipt.name");
                Date paymentDate = rs.getDate("receipt.date");
                String userCard = rs.getString("card.number");
                String userFirstName = user.getFirstName();
                String userLastName = user.getLastName();
                String paymentCardNumber = rs.getString("trans_service.number");
                String paymentFirstName = rs.getString("trans_service.first_name");
                String paymentLastName = rs.getString("trans_service.last_name");
                double amount = rs.getDouble("receipt.amount");
                String paymentStatus = rs.getString("receipt.status");
                receipt = new ReceiptDTO.Builder()
                        .paymentName(paymentName)
                        .paymentDate(paymentDate)
                        .userCard(userCard)
                        .userFirstName(userFirstName)
                        .userLastName(userLastName)
                        .paymentCardNumber(paymentCardNumber)
                        .paymentFirstName(paymentFirstName)
                        .paymentLastName(paymentLastName)
                        .amount(amount)
                        .paymentStatus(paymentStatus)
                        .build();
            }
            log.info("select receipt(card transfer) info with id: " + receiptId);
        } catch (SQLException e) {
            log.error("problem with selecting of receipt(card transfer) info with id: " + receiptId);
            log.error("Exception -  " + e);
        }
        return receipt;
    }

    public static ReceiptDTO getReceiptInfoUtilities(int receiptId, UserDTO user) {
        ReceiptDTO receipt = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_DATA_FOR_PDF_UTILITIES)) {
            statement.setInt(1, receiptId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                String paymentName = rs.getString("receipt.name");
                Date paymentDate = rs.getDate("receipt.date");
                String userCard = rs.getString("card.number");
                String userFirstName = user.getFirstName();
                String userLastName = user.getLastName();
                String meterW = rs.getString("meter_reading_water");
                String meterE = rs.getString("meter_reading_electricity");
                String meterG = rs.getString("meter_reading_gas");
                double amountW = rs.getDouble("amount_water");
                double amountE = rs.getDouble("amount_electricity");
                double amountG = rs.getDouble("amount_gas");
                double amount = rs.getDouble("receipt.amount");
                String paymentStatus = rs.getString("receipt.status");
                receipt = new ReceiptDTO.Builder()
                        .paymentName(paymentName)
                        .paymentDate(paymentDate)
                        .userCard(userCard)
                        .userFirstName(userFirstName)
                        .userLastName(userLastName)
                        .meterW(meterW)
                        .meterE(meterE)
                        .meterG(meterG)
                        .amountW(amountW)
                        .amountE(amountE)
                        .amountG(amountG)
                        .amount(amount)
                        .paymentStatus(paymentStatus)
                        .build();
            }
            log.info("select receipt(utilities payment) info with id: " + receiptId);
        } catch (SQLException e) {
            log.error("problem with selecting of receipt(utilities payment) info with id: " + receiptId);
            log.error("Exception -  " + e);
        }
        return receipt;
    }

    public static ReceiptDTO getReceiptInfoFines(int receiptId, UserDTO user) {
        ReceiptDTO receipt = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_DATA_FOR_PDF_FINES)) {
            statement.setInt(1, receiptId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                String paymentName = rs.getString("receipt.name");
                Date paymentDate = rs.getDate("receipt.date");
                String userCard = rs.getString("card.number");
                String userFirstName = user.getFirstName();
                String userLastName = user.getLastName();
                String paymentFirstName = rs.getString("fine_service.first_name");
                String paymentLastName = rs.getString("fine_service.last_name");
                String paymentPatronymic = rs.getString("fine_service.patronymic");
                String fineNumber = rs.getString("fine_service.number");
                double amount = rs.getDouble("receipt.amount");
                String paymentStatus = rs.getString("receipt.status");
                receipt = new ReceiptDTO.Builder()
                        .paymentName(paymentName)
                        .paymentDate(paymentDate)
                        .userCard(userCard)
                        .userFirstName(userFirstName)
                        .userLastName(userLastName)
                        .paymentFirstName(paymentFirstName)
                        .paymentLastName(paymentLastName)
                        .paymentPatronymic(paymentPatronymic)
                        .fineNumber(fineNumber)
                        .amount(amount)
                        .paymentStatus(paymentStatus)
                        .build();
            }
            log.info("select receipt(fines payment) info with id: " + receiptId);
        } catch (SQLException e) {
            log.error("problem with selecting of receipt(fines payment) info with id: " + receiptId);
            log.error("Exception -  " + e);
        }
        return receipt;
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
