package com.my.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class ReceiptDAO {
   public static String CREATE_NEW_ENTRY_IN_PHONE_SERVICE = "INSERT INTO phone_service(number) VALUES(?)";
   public static String CREATE_NEW_ENTRY_IN_RECEIPT_FOR_PHONE_SERVICE = "INSERT INTO receipt(name, account_id, date, status, purpose_id, amount, service_id) VALUES(?,?,?,\"prepared\", ?, ?, ?)";

    public static String CREATE_NEW_ENTRY_IN_SERV_SERVICE = "INSERT INTO serv_service(card, service) VALUES(?, ?)";

   public static int createNewEntryInPhoneService(String phoneNumber){
       int phoneId = -1;
       try(Connection connection = DBManager.getInstance().getConnection();
           PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_PHONE_SERVICE, Statement.RETURN_GENERATED_KEYS)) {
           statement.setString(1, phoneNumber);
           statement.executeUpdate();
           try(ResultSet rs = statement.getGeneratedKeys()){
               if(rs.next()){
                   phoneId = rs.getInt(1);
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return phoneId;
   }

   public static void createEntryInReceiptForPhoneService(int accountId, double amount, int serviceId){
       // java.sql.Date.valueOf(LocalDate.now());
       try(Connection connection = DBManager.getInstance().getConnection();
       PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_RECEIPT_FOR_PHONE_SERVICE)){
       statement.setString(1, "payment_"+accountId +"_" +serviceId);
       statement.setInt(2, accountId);
       statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
       statement.setInt(4, 1);
       statement.setDouble(5, amount);
       statement.setInt(6, serviceId);
       statement.executeUpdate();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }


    public static int createNewEntryInServService(String cardNumber, String serviceName){
        int servId = -1;
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_SERV_SERVICE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cardNumber);
            statement.setString(2, serviceName);
            statement.executeUpdate();
            try(ResultSet rs = statement.getGeneratedKeys()){
                if(rs.next()){
                    servId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servId;
    }

    /*public static void createEntryInReceiptForServService(int accountId,double amount, int serviceId){
        // java.sql.Date.valueOf(LocalDate.now());
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ENTRY_IN_RECEIPT_FOR_PHONE_SERVICE)){
            statement.setString(1, "payment_"+accountId +"_" +serviceId);
            statement.setInt(2, accountId);
            statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            statement.setInt(4, 2);
            statement.setDouble(5, amount);
            statement.setInt(6, serviceId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

}
