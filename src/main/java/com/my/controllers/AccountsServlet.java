package com.my.controllers;

import com.my.dao.AccountDAO;
import com.my.entities.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/accounts")
public class AccountsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Account> accounts = AccountDAO.getAllAccounts();
        req.setAttribute("list", accounts);
        String action = req.getParameter("action");
        if (Objects.nonNull(action) && action.equals("block")) {
            AccountDAO.blockAccount(Integer.parseInt(req.getParameter("id")));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            AccountDAO.unblockAccount(Integer.parseInt(req.getParameter("id")));
        }
        req.getRequestDispatcher("/views/jsp/accounts.jsp").forward(req, resp);
    }
}
