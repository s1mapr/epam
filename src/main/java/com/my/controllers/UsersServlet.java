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
    private static final String GET_USERS = "SELECT * FROM user WHERE role_id = '1' LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_LOGIN_ASC = "SELECT * FROM user WHERE role_id = '1' ORDER BY login ASC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_LOGIN_DESC = "SELECT * FROM user WHERE role_id = '1' ORDER BY login DESC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_NAME_ASC = "SELECT * FROM user WHERE role_id = '1' ORDER BY first_name ASC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_NAME_DESC = "SELECT * FROM user WHERE role_id = '1' ORDER BY first_name DESC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_LAST_NAME_ASC = "SELECT * FROM user WHERE role_id = '1' ORDER BY last_name ASC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_LAST_NAME_DESC = "SELECT * FROM user WHERE role_id = '1' ORDER BY last_name DESC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_EMAIL_ASC = "SELECT * FROM user WHERE role_id = '1' ORDER BY email ASC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_EMAIL_DESC = "SELECT * FROM user WHERE role_id = '1' ORDER BY email DESC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_PHONE_NUMBER_ASC = "SELECT * FROM user WHERE role_id = '1' ORDER BY phone_number ASC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_PHONE_NUMBER_DESC = "SELECT * FROM user WHERE role_id = '1' ORDER BY phone_number DESC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_STATUS_ASC = "SELECT * FROM user WHERE role_id = '1' ORDER BY status ASC LIMIT 5 OFFSET ?";
    private static final String GET_USERS_SORTED_BY_STATUS_DESC = "SELECT * FROM user WHERE role_id = '1' ORDER BY status DESC LIMIT 5 OFFSET ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UsersServlet#doGet");
        HttpSession session = req.getSession();
        int listLength = UserDAO.getAllUsersCount();
        int pagesCount = listLength%5==0?listLength/5:listLength/5+1;
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
        HttpSession session = req.getSession();
        String action = req.getParameter("sortAction");
        if(Objects.nonNull(action)){
            switch (action){
                case "sortLogin":
                    int usersSortLoginId = Integer.parseInt(req.getParameter("sortId"));
                    if(usersSortLoginId ==1) {
                        session.removeAttribute("usersSortLoginId");
                        session.setAttribute("usersSortLoginId", 2);
                        return GET_USERS_SORTED_BY_LOGIN_ASC;
                    }
                    session.removeAttribute("usersSortLoginId");
                    session.setAttribute("usersSortLoginId", 1);
                    return GET_USERS_SORTED_BY_LOGIN_DESC;
                case "sortName":
                    int usersSortUserName = Integer.parseInt(req.getParameter("sortId"));
                    if(usersSortUserName ==1) {
                        session.removeAttribute("usersSortUserName");
                        session.setAttribute("usersSortUserName", 2);
                        return GET_USERS_SORTED_BY_NAME_ASC;
                    }
                    session.removeAttribute("usersSortUserName");
                    session.setAttribute("usersSortUserName", 1);
                    return GET_USERS_SORTED_BY_NAME_DESC;
                case "sortLastName":
                    int usersSortUserLastName = Integer.parseInt(req.getParameter("sortId"));
                    if(usersSortUserLastName ==1) {
                        session.removeAttribute("usersSortUserLastName");
                        session.setAttribute("usersSortUserLastName", 2);
                        return GET_USERS_SORTED_BY_LAST_NAME_ASC;
                    }
                    session.removeAttribute("usersSortUserLastName");
                    session.setAttribute("usersSortUserLastName", 1);
                    return GET_USERS_SORTED_BY_LAST_NAME_DESC;
                case "sortEmail":
                    int usersSortEmailId = Integer.parseInt(req.getParameter("sortId"));
                    if(usersSortEmailId ==1) {
                        session.removeAttribute("usersSortEmailId");
                        session.setAttribute("usersSortEmailId", 2);
                        return GET_USERS_SORTED_BY_EMAIL_ASC;
                    }
                    session.removeAttribute("usersSortEmailId");
                    session.setAttribute("usersSortEmailId", 1);
                    return GET_USERS_SORTED_BY_EMAIL_DESC;
                case "sortPhoneNumber":
                    int usersSortPhoneNumberId = Integer.parseInt(req.getParameter("sortId"));
                    if(usersSortPhoneNumberId ==1) {
                        session.removeAttribute("usersSortPhoneNumberId");
                        session.setAttribute("usersSortPhoneNumberId", 2);
                        return GET_USERS_SORTED_BY_PHONE_NUMBER_ASC;
                    }
                    session.removeAttribute("usersSortPhoneNumberId");
                    session.setAttribute("usersSortPhoneNumberId", 1);
                    return GET_USERS_SORTED_BY_PHONE_NUMBER_DESC;
                case "sortStatus":
                    int usersSortStatusId = Integer.parseInt(req.getParameter("sortId"));
                    if(usersSortStatusId ==1) {
                        session.removeAttribute("usersSortStatusId");
                        session.setAttribute("usersSortStatusId", 2);
                        return GET_USERS_SORTED_BY_STATUS_ASC;
                    }
                    session.removeAttribute("usersSortStatusId");
                    session.setAttribute("usersSortStatusId", 1);
                    return GET_USERS_SORTED_BY_STATUS_DESC;

            }
        }
        session.setAttribute("usersSortLoginId", 1);
        session.setAttribute("usersSortUserName", 1);
        session.setAttribute("usersSortUserLastName", 1);
        session.setAttribute("usersSortEmailId", 1);
        session.setAttribute("usersSortPhoneNumberId", 1);
        session.setAttribute("usersSortStatusId", 1);
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
