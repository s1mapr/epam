package com.my.controllers;

import com.my.dto.AccountDTO;
import com.my.dto.UserDTO;
import com.my.service.AccountService;
import com.my.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.my.utils.HttpConstants.*;

@WebServlet(AUTHORIZATION_PATH)
public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        HttpSession session = req.getSession();
        UserDTO user = UserService.getUser(req.getParameter("login"), req.getParameter("password"));
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
        List<AccountDTO> list = AccountService.getUserAccountsWithoutPagination(user.getId());
        int notBlockedAccountCount = (int)list.stream().filter(x -> x.getStatus().equals("unblocked")).count();
        session.setAttribute("accounts", list);
        session.setAttribute("accLength", notBlockedAccountCount);
        session.setAttribute("user", user);
        if (user.getRole().equals("admin")) {
            resp.sendRedirect(MAIN_SERVLET_PATH + ADMIN_ACCOUNTS_PATH);
            return;
        }
        resp.sendRedirect(MAIN_SERVLET_PATH + USER_RECEIPTS_PATH);
    }
}
