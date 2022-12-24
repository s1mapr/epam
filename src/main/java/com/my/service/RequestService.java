package com.my.service;

import com.my.dao.AccountDAO;
import com.my.dao.RequestDAO;
import com.my.dto.RequestDTO;

import java.util.List;
import java.util.Objects;

/**
 * Request service
 */

public class RequestService {
    /**
     * Return count of all requests
     */
    public static int getListLength(){
        return RequestDAO.getCountOfRequest();
    }

    /**
     * Changes account status
     */

    public static void unblockUserAccount(String action, String id){
        if (Objects.nonNull(action) && action.equals("unblock")) {
            AccountDAO.unblockAccount(Integer.parseInt(id));
            RequestDAO.acceptRequest(Integer.parseInt(id));
        }
    }

    /**
     * Return page number for pagination
     */

    public static int getPageNumber(String page, Object pageNumberObj){
        int pageNumber;
        if (Objects.nonNull(page)) {
            pageNumber = Integer.parseInt(page);
        } else if (Objects.isNull(pageNumberObj)) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(pageNumberObj.toString());
        }
        return pageNumber;
    }

    /**
     * Return request
     */

    public static List<RequestDTO> getRequests(int page){
        return RequestDAO.getRequests(page);
    }
}
