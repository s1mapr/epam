package com.my.service;

import com.my.dao.UserDAO;
import com.my.dto.UserDTO;
import java.util.List;
import java.util.Objects;

public class UserService {
    private static final String GET_USERS = "SELECT * FROM user WHERE role_id = '1' LIMIT 10 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_LOGIN = "SELECT * FROM user WHERE role_id = '1' ORDER BY login ";
    private static final String GET_USERS_SORTED_BY_NAME = "SELECT * FROM user WHERE role_id = '1' ORDER BY first_name ";
    private static final String GET_USERS_SORTED_BY_LAST_NAME = "SELECT * FROM user WHERE role_id = '1' ORDER BY last_name ";
    private static final String GET_USERS_SORTED_BY_EMAIL = "SELECT * FROM user WHERE role_id = '1' ORDER BY email ";
    private static final String GET_USERS_SORTED_BY_PHONE_NUMBER = "SELECT * FROM user WHERE role_id = '1' ORDER BY phone_number ";
    private static final String GET_USERS_SORTED_BY_STATUS = "SELECT * FROM user WHERE role_id = '1' ORDER BY status ";

    public static UserDTO getUser(String login, String password){
        return UserDAO.getUserByLoginAndPassword(login, password);
    }
    public static boolean getUser(String login){
        return UserDAO.checkUserForRegistration(login);
    }

    public static void registration(String login, String password, String firstName, String lastName, String email, String phoneNumber){
        UserDAO.registration(login, password, firstName, lastName, email, phoneNumber);
    }

    public static int getListLength(){
        return UserDAO.getAllUsersCount();
    }

    public static void changeUserStatus(String action, String id){
        if (Objects.nonNull(action) && action.equals("block")) {
            UserDAO.blockUser(Integer.parseInt(id));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            UserDAO.unblockUser(Integer.parseInt(id));
        }
    }

    public static int getPageNumber(String page, Integer pageNumberObj){
        int pageNumber;
        if (Objects.nonNull(page)){
            pageNumber = Integer.parseInt(page);
        } else if (Objects.isNull(pageNumberObj)) {
            pageNumber = 1;
        } else {
            pageNumber = pageNumberObj;
        }
        return pageNumber;
    }

    public static String getQuery(String action, String type, String oldQuery) {
        if(Objects.nonNull(oldQuery) &&Objects.isNull(action)){
            return oldQuery;
        } else if (Objects.nonNull(action)) {
            switch (action) {
                case "sortLogin":
                    return GET_USERS_SORTED_BY_LOGIN + type + " LIMIT 10 OFFSET ?";
                case "sortName":
                    return GET_USERS_SORTED_BY_NAME + type + " LIMIT 10 OFFSET ?";
                case "sortLastName":
                    return GET_USERS_SORTED_BY_LAST_NAME + type + " LIMIT 10 OFFSET ?";
                case "sortEmail":
                    return GET_USERS_SORTED_BY_EMAIL + type + " LIMIT 10 OFFSET ?";
                case "sortPhoneNumber":
                    return GET_USERS_SORTED_BY_PHONE_NUMBER + type + " LIMIT 10 OFFSET ?";
                case "sortStatus":
                    return GET_USERS_SORTED_BY_STATUS + type + " LIMIT 10 OFFSET ?";
            }
        }
        return GET_USERS;
    }

    public static List<UserDTO> getAllUsersWithPagination(int pageNumber, String newQuery){
        return UserDAO.usersPagination(pageNumber, newQuery);
    }

    public static void setNewAvatar(String imgUrl, int userId){
        UserDAO.setNewAvatar(imgUrl, userId);
    }

    public static UserDTO updateUserData(UserDTO user, String firstName, String lastName, String email, String phoneNumber, int userId){
        UserDAO.updateUserData(firstName, lastName, email, phoneNumber, userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        return user;
    }
}
