package com.my.service;

import com.my.dao.UserDAO;
import com.my.dto.UserDTO;
import java.util.List;
import java.util.Objects;
/**
 * User service
 */
public class UserService {
    private static final String GET_USERS = "SELECT * FROM user WHERE role_id = '1' LIMIT 10 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_LOGIN = "SELECT * FROM user WHERE role_id = '1' ORDER BY login ";
    private static final String GET_USERS_SORTED_BY_NAME = "SELECT * FROM user WHERE role_id = '1' ORDER BY first_name ";
    private static final String GET_USERS_SORTED_BY_LAST_NAME = "SELECT * FROM user WHERE role_id = '1' ORDER BY last_name ";
    private static final String GET_USERS_SORTED_BY_EMAIL = "SELECT * FROM user WHERE role_id = '1' ORDER BY email ";
    private static final String GET_USERS_SORTED_BY_PHONE_NUMBER = "SELECT * FROM user WHERE role_id = '1' ORDER BY phone_number ";
    private static final String GET_USERS_SORTED_BY_STATUS = "SELECT * FROM user WHERE role_id = '1' ORDER BY status ";

    /**
     * Return UserDto object if there are user in database with same login and password
     */

    public static UserDTO getUser(String login, String password){
        return UserDAO.getUserByLoginAndPassword(login, password);
    }
    /**
     * Return true if user with same login exist
     */
    public static boolean getUser(String login){
        return UserDAO.checkUserForRegistration(login);
    }
    /**
     * Create new entry in database
     */
    public static void registration(String login, String password, String firstName, String lastName, String email, String phoneNumber){
        UserDAO.registration(login, password, firstName, lastName, email, phoneNumber);
    }

    /**
     * Return count of user accounts
     */
    public static int getListLength(){
        return UserDAO.getAllUsersCount();
    }

    /**
     * Changes user status
     */

    public static void changeUserStatus(String action, String id){
        if (Objects.nonNull(action) && action.equals("block")) {
            UserDAO.blockUser(Integer.parseInt(id));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            UserDAO.unblockUser(Integer.parseInt(id));
        }
    }

    /**
     * Return page number of pagination
     */

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

    /**
     * Return query for pagination
     */

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

    /**
     * Return users for pagination
     */

    public static List<UserDTO> getAllUsersWithPagination(int pageNumber, String newQuery){
        return UserDAO.usersPagination(pageNumber, newQuery);
    }

    /**
     * Change users profile picture
     */

    public static void setNewAvatar(String imgUrl, int userId){
        UserDAO.setNewAvatar(imgUrl, userId);
    }

    /**
     * Updates user data
     */

    public static UserDTO updateUserData(UserDTO user, String firstName, String lastName, String email, String phoneNumber, int userId){
        UserDAO.updateUserData(firstName, lastName, email, phoneNumber, userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        return user;
    }
}
