package com.my.controllers;

import com.my.dao.UserDAO;
import com.my.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private static final String GET_USERS = "SELECT * FROM user WHERE role_id = '1' LIMIT 10 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_LOGIN = "SELECT * FROM user WHERE role_id = '1' ORDER BY login ";
    private static final String GET_USERS_SORTED_BY_NAME = "SELECT * FROM user WHERE role_id = '1' ORDER BY first_name ";
    private static final String GET_USERS_SORTED_BY_LAST_NAME = "SELECT * FROM user WHERE role_id = '1' ORDER BY last_name ";
    private static final String GET_USERS_SORTED_BY_EMAIL = "SELECT * FROM user WHERE role_id = '1' ORDER BY email ";
    private static final String GET_USERS_SORTED_BY_PHONE_NUMBER = "SELECT * FROM user WHERE role_id = '1' ORDER BY phone_number ";
    private static final String GET_USERS_SORTED_BY_STATUS = "SELECT * FROM user WHERE role_id = '1' ORDER BY status ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UsersServlet#doGet");
        HttpSession session = req.getSession();
        int listLength = UserDAO.getAllUsersCount();
        int pagesCount = listLength%10==0?listLength/10:listLength/10+1;
        req.setAttribute("pagesCount", pagesCount);
        List<User> users;
        String action = req.getParameter("action");
        if (Objects.nonNull(action) && action.equals("block")) {
            UserDAO.blockUser(Integer.parseInt(req.getParameter("id")));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            UserDAO.unblockUser(Integer.parseInt(req.getParameter("id")));
        }
        if(Objects.nonNull(session.getAttribute("usersQuery"))&&Objects.isNull(req.getParameter("sortAction"))){
            users = getUsers(req, session, session.getAttribute("usersQuery").toString());
        }else{
            String query = getQuery(req);
            users = getUsers(req, session,  query);
        }

        req.setAttribute("list", users);
        req.getRequestDispatcher("/views/jsp/users.jsp").forward(req, resp);
    }

    private static String getQuery(HttpServletRequest req){
        String action = req.getParameter("sortAction");
        if(Objects.nonNull(action)){
            String type = req.getParameter("type");
            switch (action){
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

    private static List<User> getUsers(HttpServletRequest req, HttpSession session, String query) {
        session.removeAttribute("usersQuery");
        session.setAttribute("usersQuery", query);
        Object pageNumberStr = session.getAttribute("userPage");
        int pageNumber;
        if(Objects.nonNull(req.getParameter("page"))){
            pageNumber = Integer.parseInt(req.getParameter("page"));
            session.removeAttribute("userPage");
            session.setAttribute("userPage", pageNumber);
        }else if(Objects.isNull(pageNumberStr)){
            pageNumber = 1;
            session.setAttribute("userPage", pageNumber);
        }
        else{
            pageNumber = Integer.parseInt(session.getAttribute("userPage").toString());
        }

        return UserDAO.usersPagination(pageNumber, query);
    }

}
