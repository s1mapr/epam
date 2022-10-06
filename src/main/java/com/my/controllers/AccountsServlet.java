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
    private static final String GET_ACCOUNTS = "SELECT * FROM account JOIN user ON user.id = account.user_id LIMIT 5 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_ACCOUNT_NAME_ASC = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY account.name ASC LIMIT 5 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_ACCOUNT_NAME_DESC = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY account.name DESC LIMIT 5 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_LOGIN_ASC = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY login ASC LIMIT 5 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_LOGIN_DESC = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY login DESC LIMIT 5 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_USER_NAME_ASC = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY user.first_name ASC LIMIT 5 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_USER_NAME_DESC = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY user.first_name DESC LIMIT 5 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_USER_LAST_NAME_ASC = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY user.last_name ASC LIMIT 5 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_USER_LAST_NAME_DESC = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY user.last_name DESC LIMIT 5 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_STATUS_ASC = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY account.status ASC LIMIT 5 OFFSET ?";
    private static final String GET_ACCOUNTS_SORTED_BY_STATUS_DESC = "SELECT * FROM account JOIN user ON user.id = account.user_id ORDER BY account.status DESC LIMIT 5 OFFSET ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int listLength = AccountDAO.getAllAccountsCount();
        int pagesCount = listLength%5==0?listLength/5:listLength/5+1;
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
        HttpSession session = req.getSession();
        String action = req.getParameter("sortAction");
        if(Objects.nonNull(action)){
            switch (action){
                case "sortAccountName":
                    int accountsSortAccountNameId = Integer.parseInt(req.getParameter("sortId"));
                    if(accountsSortAccountNameId ==1) {
                        session.removeAttribute("accountsSortAccountNameId");
                        session.setAttribute("accountsSortAccountNameId", 2);
                        return GET_ACCOUNTS_SORTED_BY_ACCOUNT_NAME_ASC;
                    }
                    session.removeAttribute("accountsSortAccountNameId");
                    session.setAttribute("accountsSortAccountNameId", 1);
                    return GET_ACCOUNTS_SORTED_BY_ACCOUNT_NAME_DESC;
                case "sortLogin":
                    int accountsSortLoginId = Integer.parseInt(req.getParameter("sortId"));
                    if(accountsSortLoginId ==1) {
                        session.removeAttribute("accountsSortLoginId");
                        session.setAttribute("accountsSortLoginId", 2);
                        return GET_ACCOUNTS_SORTED_BY_LOGIN_ASC;
                    }
                    session.removeAttribute("accountsSortLoginId");
                    session.setAttribute("accountsSortLoginId", 1);
                    return GET_ACCOUNTS_SORTED_BY_LOGIN_DESC;
                case "sortName":
                    int accountsSortUserName = Integer.parseInt(req.getParameter("sortId"));
                    if(accountsSortUserName ==1) {
                        session.removeAttribute("accountsSortUserName");
                        session.setAttribute("accountsSortUserName", 2);
                        return GET_ACCOUNTS_SORTED_BY_USER_NAME_ASC;
                    }
                    session.removeAttribute("accountsSortUserName");
                    session.setAttribute("accountsSortUserName", 1);
                    return GET_ACCOUNTS_SORTED_BY_USER_NAME_DESC;
                case "sortLastName":
                    int accountsSortUserLastName = Integer.parseInt(req.getParameter("sortId"));
                    if(accountsSortUserLastName ==1) {
                        session.removeAttribute("accountsSortUserLastName");
                        session.setAttribute("accountsSortUserLastName", 2);
                        return GET_ACCOUNTS_SORTED_BY_USER_LAST_NAME_ASC;
                    }
                    session.removeAttribute("accountsSortUserLastName");
                    session.setAttribute("accountsSortUserLastName", 1);
                    return GET_ACCOUNTS_SORTED_BY_USER_LAST_NAME_DESC;
                case "sortStatus":
                    int accountsSortStatusId = Integer.parseInt(req.getParameter("sortId"));
                    if(accountsSortStatusId ==1) {
                        session.removeAttribute("accountsSortStatusId");
                        session.setAttribute("accountsSortStatusId", 2);
                        return GET_ACCOUNTS_SORTED_BY_STATUS_ASC;
                    }
                    session.removeAttribute("accountsSortStatusId");
                    session.setAttribute("accountsSortStatusId", 1);
                    return GET_ACCOUNTS_SORTED_BY_STATUS_DESC;

            }
        }
        session.setAttribute("accountsSortAccountNameId", 1);
        session.setAttribute("accountsSortLoginId", 1);
        session.setAttribute("accountsSortUserName", 1);
        session.setAttribute("accountsSortUserLastName", 1);
        session.setAttribute("accountsSortStatusId", 1);
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
