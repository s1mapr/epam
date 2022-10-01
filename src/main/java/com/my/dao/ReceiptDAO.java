package com.my.dao;

import java.sql.*;
import java.util.Random;

public class ReceiptDAO {
   public static String CREATE_NEW_ENTRY_IN_PHONE_SERVICE = "INSERT INTO phone_service(number) VALUES(?)";

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



}
