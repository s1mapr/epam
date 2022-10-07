package com.my.controllers;

import com.my.dao.AccountDAO;
import com.my.dao.UserDAO;
import com.my.entities.Account;
import com.my.entities.User;
import com.my.utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AuthorizationServlet#doGet");
        req.getRequestDispatcher("/views/jsp/authorization.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AuthorizationServlet#doPost");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = UserDAO.getUserByLoginAndPassword(login, password);
        HttpSession session = req.getSession();
        if(Objects.isNull(user)) {
            session.setAttribute("error", "wrong login or password");
            resp.sendRedirect("/epamProject/authorization");
            return;
        }

        List<Account> list = AccountDAO.getUserAccountsWithoutPagination(user.getId());
        if(!list.isEmpty()){
            session.setAttribute("accounts", list);

        }
        session.setAttribute("user", user);
        if(user.getRole().equals("admin")){
            resp.sendRedirect("/epamProject/accounts");
            return;
        }
        resp.sendRedirect("/epamProject/mainPage");

    }
}
