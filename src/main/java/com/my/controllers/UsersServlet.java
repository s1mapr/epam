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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UsersServlet#doGet");
        HttpSession session = req.getSession();
        int listLength = UserDAO.getAllUsersCount();
        String action = req.getParameter("action");
        if (Objects.nonNull(action) && action.equals("block")) {
            UserDAO.blockUser(Integer.parseInt(req.getParameter("id")));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            UserDAO.unblockUser(Integer.parseInt(req.getParameter("id")));
        }
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
        List<User> users = UserDAO.usersPagination(pageNumber);
        int pagesCount = listLength%5==0?listLength/5:listLength/5+1;
        req.setAttribute("pagesCount", pagesCount);
        req.setAttribute("list", users);
        req.getRequestDispatcher("/views/jsp/users.jsp").forward(req, resp);
    }

}
