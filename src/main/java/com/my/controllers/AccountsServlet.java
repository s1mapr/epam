package com.my.controllers;

import com.my.dao.AccountDAO;
import com.my.entities.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/accounts")
public class AccountsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String action = req.getParameter("action");
        String pagAction = req.getParameter("pagAction");
        if (Objects.nonNull(action) && action.equals("block")) {
            AccountDAO.blockAccount(Integer.parseInt(req.getParameter("id")));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            AccountDAO.unblockAccount(Integer.parseInt(req.getParameter("id")));
        }
        Object pageNumberStr = session.getAttribute("accPage");
        int pageNumber =0;
        if (Objects.nonNull(pageNumberStr) && Objects.nonNull(pagAction)) {
            if(pagAction.equals("next")){
                pageNumber = (Integer.parseInt(session.getAttribute("accPage").toString()));
                session.removeAttribute("accPage");
                session.setAttribute("accPage", ++pageNumber);
            }else if(pagAction.equals("prev")){
                pageNumber = (Integer.parseInt(session.getAttribute("accPage").toString()));
                session.removeAttribute("accPage");
                session.setAttribute("accPage", --pageNumber);
            }

        }else if(Objects.isNull(pageNumberStr)){
            pageNumber = 1;
            session.setAttribute("accPage", pageNumber);
        }
        else{
            pageNumber = (Integer.parseInt(session.getAttribute("accPage").toString()));
        }

        List<Account> accounts = AccountDAO.accountPagination(pageNumber);
        int listLength = AccountDAO.getAllAccountsCount();
        req.setAttribute("listLength", listLength);
        req.setAttribute("list", accounts);
        req.getRequestDispatcher("/views/jsp/accounts.jsp").forward(req, resp);
    }
}
