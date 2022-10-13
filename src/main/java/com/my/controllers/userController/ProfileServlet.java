package com.my.controllers.userController;

import com.my.dao.AccountDAO;
import com.my.dao.RequestDAO;
import com.my.dao.UserDAO;
import com.my.entities.Account;
import com.my.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/user/profile")
public class ProfileServlet extends HttpServlet {
    private static final String GET_USER_ACCOUNTS = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? LIMIT 8 OFFSET ?";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_NAME = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY name ";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_CARD_NUMBER = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY card.number ";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_AMOUNT = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY card.amount ";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_STATUS = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY status ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ProfileServlet#doGet");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<Account> list;
        req.removeAttribute("accounts");
        int listLength = AccountDAO.getCountOfUsersAccounts(user.getId());
        int pagesCount = listLength % 8 == 0 ? listLength / 8 : listLength / 8 + 1;
        req.setAttribute("pagesCount", pagesCount);
        String action = req.getParameter("action");
        if (Objects.nonNull(action) && action.equals("block")) {
            AccountDAO.blockAccount(Integer.parseInt(req.getParameter("id")));
        } else if (Objects.nonNull(action) && action.equals("unblock")) {
            AccountDAO.setPendingStatus(Integer.parseInt(req.getParameter("id")));
            RequestDAO.createNewRequest(Integer.parseInt(req.getParameter("id")));
        }
        if (Objects.nonNull(session.getAttribute("profileQuery")) && Objects.isNull(req.getParameter("sortAction"))) {
            list = getAccounts(req, session, user, session.getAttribute("profileQuery").toString());
        } else {
            String query = getQuery(req);
            list = getAccounts(req, session, user, query);
        }
        List<Account> accountList = AccountDAO.getUserAccountsWithoutPagination(user.getId());
        session.removeAttribute("accounts");
        session.setAttribute("accounts", accountList);
        req.setAttribute("accountsPag", list);
        req.getRequestDispatcher("/views/jsp/profile.jsp").forward(req, resp);
    }

    private static String getQuery(HttpServletRequest req) {
        String action = req.getParameter("sortAction");
        if (Objects.nonNull(action)) {
            String type = req.getParameter("type");
            switch (action) {
                case "sortName":
                    return GET_USER_ACCOUNTS_SORTED_BY_NAME + type + " LIMIT 8 OFFSET ?";
                case "sortCardNumber":
                    return GET_USER_ACCOUNTS_SORTED_BY_CARD_NUMBER + type + " LIMIT 8 OFFSET ?";
                case "sortAmount":
                    return GET_USER_ACCOUNTS_SORTED_BY_AMOUNT + type + " LIMIT 8 OFFSET ?";
                case "sortStatus":
                    return GET_USER_ACCOUNTS_SORTED_BY_STATUS + type + " LIMIT 8 OFFSET ?";
            }
        }
        return GET_USER_ACCOUNTS;
    }

    private static List<Account> getAccounts(HttpServletRequest req, HttpSession session, User user, String query) {
        session.removeAttribute("profileQuery");
        session.setAttribute("profileQuery", query);
        Object pageNumberStr = session.getAttribute("profPage");
        int pageNumber;
        if (Objects.nonNull(req.getParameter("page"))) {
            pageNumber = Integer.parseInt(req.getParameter("page"));
            session.removeAttribute("profPage");
            session.setAttribute("profPage", pageNumber);
        } else if (Objects.isNull(pageNumberStr)) {
            pageNumber = 1;
            session.setAttribute("profPage", pageNumber);
        } else {
            pageNumber = Integer.parseInt(session.getAttribute("profPage").toString());
        }
        return AccountDAO.getUserAccounts(user.getId(), pageNumber, query);
    }

}
