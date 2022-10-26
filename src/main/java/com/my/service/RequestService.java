package com.my.service;

import com.my.dao.AccountDAO;
import com.my.dao.RequestDAO;
import com.my.dto.RequestDTO;

import java.util.List;
import java.util.Objects;

public class RequestService {
    public static int getListLength(){
        return RequestDAO.getCountOfRequest();
    }

    public static void unblockUserAccount(String action, String id){
        if (Objects.nonNull(action) && action.equals("unblock")) {
            AccountDAO.unblockAccount(Integer.parseInt(id));
            RequestDAO.acceptRequest(Integer.parseInt(id));
        }
    }

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

    public static List<RequestDTO> getRequests(int page){
        return RequestDAO.getRequests(page);
    }
}
