package com.my.service;

import com.my.dao.AccountDAO;
import com.my.dao.RequestDAO;
import com.my.dao.UserDAO;
import com.my.dto.AccountDTO;
import com.my.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

public class AccountService {
    private static final String GET_ACCOUNTS = "SELECT * FROM account JOIN user ON user.id = account.user_id LIMIT 10 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_ACCOUNT_NAME = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY account.name ";
    private static final String GET_ACCOUNTS_SORTED_BY_LOGIN = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY login ";
    private static final String GET_ACCOUNTS_SORTED_BY_USER_NAME = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY user.first_name ";
    private static final String GET_ACCOUNTS_SORTED_BY_USER_LAST_NAME = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY user.last_name ";
    private static final String GET_ACCOUNTS_SORTED_BY_STATUS = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY account.status ";
    private static final String GET_USER_ACCOUNTS = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? LIMIT 8 OFFSET ?";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_NAME = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY name ";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_CARD_NUMBER = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY card.number ";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_AMOUNT = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY card.amount ";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_STATUS = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY status ";

    public static int getListLength() {
        return AccountDAO.getAllAccountsCount();
    }
    public static int getUserAccountsListLength(int id){
        return AccountDAO.getCountOfUsersAccounts(id);
    }

    public static void changeAccountStatusAdmin(String action, String id){
        if (Objects.nonNull(action) && action.equals("block")) {
            AccountDAO.blockAccount(Integer.parseInt(id));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            AccountDAO.unblockAccount(Integer.parseInt(id));
        }
    }

    public static void changeAccountStatusUser(String action, String id){
        if (Objects.nonNull(action) && action.equals("block")) {
            AccountDAO.blockAccount(Integer.parseInt(id));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            AccountDAO.setPendingStatus(Integer.parseInt(id));
            RequestDAO.createNewRequest(Integer.parseInt(id));
        }
    }

    public static List<AccountDTO> getAllAccountsWithPagination(int pageNumber,String newQuery){
        return AccountDAO.accountPagination(pageNumber, newQuery);
    }

    public static String getQueryForAdminAccounts(String action, String type, String oldQuery) {
        if(Objects.nonNull(oldQuery) &&Objects.isNull(action)){
            return oldQuery;
        } else if (Objects.nonNull(action)) {
            switch (action) {
                case "sortAccountName":
                    return GET_ACCOUNTS_SORTED_BY_ACCOUNT_NAME + type + " LIMIT 10 OFFSET ?";
                case "sortLogin":
                    return GET_ACCOUNTS_SORTED_BY_LOGIN + type + " LIMIT 10 OFFSET ?";
                case "sortName":
                    return GET_ACCOUNTS_SORTED_BY_USER_NAME + type + " LIMIT 10 OFFSET ?";
                case "sortLastName":
                    return GET_ACCOUNTS_SORTED_BY_USER_LAST_NAME + type + " LIMIT 10 OFFSET ?";
                case "sortStatus":
                    return GET_ACCOUNTS_SORTED_BY_STATUS + type + " LIMIT 10 OFFSET ?";
            }
        }
        return GET_ACCOUNTS;
    }

    public static String getQueryForUserAccounts(String action, String type, String oldQuery) {
        if (Objects.nonNull(oldQuery) && Objects.isNull(action)) {
            return oldQuery;
        }
            if (Objects.nonNull(action)) {
                switch (action) {
                    case "sortName":
                        return GET_USER_ACCOUNTS_SORTED_BY_NAME + type + " LIMIT 8 OFFSET ?";
                    case "sortCardNumber":
                        return GET_USER_ACCOUNTS_SORTED_BY_CARD_NUMBER + type + " LIMIT 8 OFFSET ?";
                    case "sortAmount":
                        return GET_USER_ACCOUNTS_SORTED_BY_AMOUNT + type + " LIMIT 8 OFFSET ?";
                    case "sortStatus":
                        return GET_USER_ACCOUNTS_SORTED_BY_STATUS + type + " LIMIT 8 OFFSET ?";
                }
            }
            return GET_USER_ACCOUNTS;
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

    public static List<AccountDTO> getUserAccountsWithoutPagination(int userId){
        return AccountDAO.getUserAccountsWithoutPagination(userId);
    }

    public static List<AccountDTO> getUserAccountsWithPagination(int id,int pageNumber, String newQuery){
        return AccountDAO.getUserAccounts(id, pageNumber, newQuery);
    }

    public static int getCardId(int accountId){
        return AccountDAO.getCardId(accountId);
    }

    public static void createNewAccount(String name, int cardId, int userId){
        AccountDAO.addNewAccount(name, cardId, userId);
    }

    public static boolean checkAccountNameIfExist(String name, int userId){
        return AccountDAO.checkAccountName(name, userId);
    }

}
