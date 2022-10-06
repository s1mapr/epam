package com.my.controllers;

import com.my.dao.AccountDAO;
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

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final String GET_USER_ACCOUNTS = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? LIMIT 5 OFFSET ?";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_NAME_ASC = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY name ASC LIMIT 5 OFFSET ?";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_NAME_DESC = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY name DESC LIMIT 5 OFFSET ?";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_CARD_NUMBER_ASC = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY card.number ASC LIMIT 5 OFFSET ?";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_CARD_NUMBER_DESC = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY card.number DESC LIMIT 5 OFFSET ?";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_AMOUNT_ASC = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY card.amount ASC LIMIT 5 OFFSET ?";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_AMOUNT_DESC = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY card.amount DESC LIMIT 5 OFFSET ?";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_STATUS_ASC = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY status ASC LIMIT 5 OFFSET ?";
    private static final String GET_USER_ACCOUNTS_SORTED_BY_STATUS_DESC = "SELECT * FROM account JOIN card ON card.id = account.card_id WHERE user_id = ? ORDER BY status DESC LIMIT 5 OFFSET ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ProfileServlet#doGet");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<Account> list;
        req.removeAttribute("accounts");
        int listLength = AccountDAO.getCountOfUsersAccounts(user.getId());
        int pagesCount = listLength % 5 == 0 ? listLength / 5 : listLength / 5 + 1;
        req.setAttribute("pagesCount", pagesCount);
        String action = req.getParameter("action");
        if (Objects.nonNull(action) && action.equals("block")) {
            AccountDAO.blockAccount(Integer.parseInt(req.getParameter("id")));
        }
        if(Objects.nonNull(session.getAttribute("profileQuery"))&&Objects.isNull(req.getParameter("sortAction"))){
            list = getAccounts(req, session,user ,session.getAttribute("profileQuery").toString());
        }else{
            String query = getQuery(req);
            list = getAccounts(req, session, user ,query);
        }
        req.setAttribute("accounts", list);
        req.getRequestDispatcher("/views/jsp/profile.jsp").forward(req, resp);
    }

    private static String getQuery(HttpServletRequest req){
        HttpSession session = req.getSession();
        String action = req.getParameter("sortAction");
        if(Objects.nonNull(action)){
            switch (action){
                case "sortName":
                    int profileSortNameId = Integer.parseInt(req.getParameter("sortId"));
                    if(profileSortNameId ==1) {
                        session.removeAttribute("profileSortNameId");
                        session.setAttribute("profileSortNameId", 2);
                        return GET_USER_ACCOUNTS_SORTED_BY_NAME_ASC;
                    }
                    session.removeAttribute("profileSortNameId");
                    session.setAttribute("profileSortNameId", 1);
                    return GET_USER_ACCOUNTS_SORTED_BY_NAME_DESC;
                case "sortCardNumber":
                    int profileSortCardNumberId = Integer.parseInt(req.getParameter("sortId"));
                    if(profileSortCardNumberId ==1) {
                        session.removeAttribute("profileSortCardNumberId");
                        session.setAttribute("profileSortCardNumberId", 2);
                        return GET_USER_ACCOUNTS_SORTED_BY_CARD_NUMBER_ASC;
                    }
                    session.removeAttribute("profileSortCardNumberId");
                    session.setAttribute("profileSortCardNumberId", 1);
                    return GET_USER_ACCOUNTS_SORTED_BY_CARD_NUMBER_DESC;
                case "sortAmount":
                    int profileSortAmountId = Integer.parseInt(req.getParameter("sortId"));
                    if(profileSortAmountId ==1) {
                        session.removeAttribute("profileSortAmountId");
                        session.setAttribute("profileSortAmountId", 2);
                        return GET_USER_ACCOUNTS_SORTED_BY_AMOUNT_ASC;
                    }
                    session.removeAttribute("profileSortAmountId");
                    session.setAttribute("profileSortAmountId", 1);
                    return GET_USER_ACCOUNTS_SORTED_BY_AMOUNT_DESC;
                case "sortStatus":
                    int profileSortStatusId = Integer.parseInt(req.getParameter("sortId"));
                    if(profileSortStatusId ==1) {
                        session.removeAttribute("profileSortStatusId");
                        session.setAttribute("profileSortStatusId", 2);
                        return GET_USER_ACCOUNTS_SORTED_BY_STATUS_ASC;
                    }
                    session.removeAttribute("profileSortStatusId");
                    session.setAttribute("profileSortStatusId", 1);
                    return GET_USER_ACCOUNTS_SORTED_BY_STATUS_DESC;
            }
        }
        session.setAttribute("profileSortNameId", 1);
        session.setAttribute("profileSortCardNumberId", 1);
        session.setAttribute("profileSortAmountId", 1);
        session.setAttribute("profileSortStatusId", 1);
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
