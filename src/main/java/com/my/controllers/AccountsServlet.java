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
    private static final String GET_ACCOUNTS = "SELECT * FROM account JOIN user ON user.id = account.user_id LIMIT 10 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_ACCOUNT_NAME = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY account.name ";
    private static final String GET_ACCOUNTS_SORTED_BY_LOGIN = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY login ";
    private static final String GET_ACCOUNTS_SORTED_BY_USER_NAME = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY user.first_name ";
    private static final String GET_ACCOUNTS_SORTED_BY_USER_LAST_NAME = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY user.last_name ";
    private static final String GET_ACCOUNTS_SORTED_BY_STATUS = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY account.status ";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int listLength = AccountDAO.getAllAccountsCount();
        int pagesCount = listLength%10==0?listLength/10:listLength/10+1;
        req.setAttribute("pagesCount", pagesCount);
        List<Account> accounts;
        String action = req.getParameter("action");
        if (Objects.nonNull(action) && action.equals("block")) {
            AccountDAO.blockAccount(Integer.parseInt(req.getParameter("id")));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            AccountDAO.unblockAccount(Integer.parseInt(req.getParameter("id")));
        }
        if(Objects.nonNull(session.getAttribute("accountQuery"))&&Objects.isNull(req.getParameter("sortAction"))){
            accounts = getAccounts(req, session, session.getAttribute("accountQuery").toString());
        }else{
            String query = getQuery(req);
            accounts = getAccounts(req, session,  query);
        }

        req.setAttribute("list", accounts);
        req.getRequestDispatcher("/views/jsp/accounts.jsp").forward(req, resp);
    }

    private static String getQuery(HttpServletRequest req){
        String action = req.getParameter("sortAction");
        if(Objects.nonNull(action)){
            String type = req.getParameter("type");
            switch (action){
                case "sortAccountName":
                    return GET_ACCOUNTS_SORTED_BY_ACCOUNT_NAME + type + " LIMIT 10 OFFSET ?";
                case "sortLogin":
                    return GET_ACCOUNTS_SORTED_BY_LOGIN + type + " LIMIT 10 OFFSET ?";
                case "sortName":
                    return GET_ACCOUNTS_SORTED_BY_USER_NAME + type + " LIMIT 10 OFFSET ?";
                case "sortLastName":
                    return GET_ACCOUNTS_SORTED_BY_USER_LAST_NAME + type + " LIMIT 10 OFFSET ?";
                case "sortStatus":
                    return GET_ACCOUNTS_SORTED_BY_STATUS + type + " LIMIT 10 OFFSET ?";
            }
        }
        return GET_ACCOUNTS;
    }

    private static List<Account> getAccounts(HttpServletRequest req, HttpSession session, String query) {
        session.removeAttribute("accountQuery");
        session.setAttribute("accountQuery", query);

        Object pageNumberStr = session.getAttribute("accPage");
        int pageNumber;
        if(Objects.nonNull(req.getParameter("page"))){
            pageNumber = Integer.parseInt(req.getParameter("page"));
            session.removeAttribute("accPage");
            session.setAttribute("accPage", pageNumber);
        }else if(Objects.isNull(pageNumberStr)){
            pageNumber = 1;
            session.setAttribute("accPage", pageNumber);
        }
        else{
            pageNumber = Integer.parseInt(session.getAttribute("accPage").toString());
        }
        return AccountDAO.accountPagination(pageNumber, query);
    }
}
