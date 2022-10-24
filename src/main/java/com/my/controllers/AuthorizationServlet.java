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
import java.util.stream.Collectors;

import static com.my.utils.HttpConstants.*;

@WebServlet(AUTHORIZATION_PATH)
public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AuthorizationServlet#doGet");
        HttpSession session = req.getSession();
        if (Objects.nonNull(session.getAttribute("youAreBlocked"))) {
            session.removeAttribute("youAreBlocked");
            req.setAttribute("youAreBlocked", "msg");
        }
        if (Objects.nonNull(session.getAttribute("loginError"))) {
            session.removeAttribute("loginError");
            req.setAttribute("loginError", "msg");
        }

        req.getRequestDispatcher("/views/jsp/authorization.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AuthorizationServlet#doPost");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = UserDAO.getUserByLoginAndPassword(login, password);
        HttpSession session = req.getSession();
        if (Objects.isNull(user)) {
            session.setAttribute("loginError", "msg");
            resp.sendRedirect(MAIN_SERVLET_PATH + AUTHORIZATION_PATH);
            return;
        }
        if (user.getStatus().equals("blocked")) {
            session.setAttribute("youAreBlocked", "msg");
            resp.sendRedirect(MAIN_SERVLET_PATH + AUTHORIZATION_PATH);
            return;
        }
        List<Account> list = AccountDAO.getUserAccountsWithoutPagination(user.getId());
        session.setAttribute("accounts", list);
        int notBlockedAccountCount = (int)list.stream().filter(x -> x.getStatus().equals("unblocked")).count();
        session.setAttribute("accLength", notBlockedAccountCount);
        session.setAttribute("user", user);
        if (user.getRole().equals("admin")) {
            resp.sendRedirect(MAIN_SERVLET_PATH + ADMIN_ACCOUNTS_PATH);
            return;
        }
        resp.sendRedirect(MAIN_SERVLET_PATH + USER_RECEIPTS_PATH);
    }
}
