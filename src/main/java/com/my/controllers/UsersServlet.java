package com.my.controllers;

import com.my.dao.AccountDAO;
import com.my.dao.UserDAO;
import com.my.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UsersServlet#doGet");
        String action = req.getParameter("action");
        if (Objects.nonNull(action) && action.equals("block")) {
            UserDAO.blockUser(Integer.parseInt(req.getParameter("id")));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            UserDAO.unblockUser(Integer.parseInt(req.getParameter("id")));
        }
        List<User> userList = UserDAO.getAllUsers();
        req.setAttribute("list", userList);
        req.getRequestDispatcher("/views/jsp/users.jsp").forward(req, resp);
    }
}
