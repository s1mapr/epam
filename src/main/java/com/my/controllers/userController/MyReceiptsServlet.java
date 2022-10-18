package com.my.controllers.userController;

import com.my.dao.ReceiptDAO;
import com.my.entities.Receipt;
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

import static com.my.utils.HttpConstants.*;

@WebServlet(USER_RECEIPTS_PATH)
public class MyReceiptsServlet extends HttpServlet {
    public static String GET_RECEIPTS = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_AMOUNT = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY amount ";
    public static String GET_RECEIPTS_SORTED_BY_NAME = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY receipt.name ";
    public static String GET_RECEIPTS_SORTED_BY_DATE = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY date ";
    public static String GET_RECEIPTS_SORTED_BY_STATUS = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY receipt.status ";
    public static String GET_RECEIPTS_SORTED_BY_PURPOSE = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY purpose.name ";
    public static String GET_RECEIPTS_SORTED_BY_ACCOUNT = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY account.name ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MyReceiptsServlet#doGet");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        ReceiptDAO.updateStatus();
        int listLength = ReceiptDAO.getPaymentsCountOfUser(user.getId());
        int pagesCount = listLength % 10 == 0 ? listLength / 10 : listLength / 10 + 1;
        req.setAttribute("pagesCount", pagesCount);
        List<Receipt> list;

        if (Objects.nonNull(session.getAttribute("query")) && Objects.isNull(req.getParameter("sortAction"))) {
            list = getReceipts(req, session, user, session.getAttribute("query").toString());
        } else {
            String query = getQuery(req);
            list = getReceipts(req, session, user, query);
        }
        req.setAttribute("list", list);
        req.getRequestDispatcher("/views/jsp/myReceipts.jsp").forward(req, resp);
    }

    private static String getQuery(HttpServletRequest req) {
        String action = req.getParameter("sortAction");
        if (Objects.nonNull(action)) {
            String type = req.getParameter("type");
            switch (action) {
                case "sortAmount":
                    return GET_RECEIPTS_SORTED_BY_AMOUNT + type + " LIMIT 10 OFFSET ?";
                case "sortName":
                    return GET_RECEIPTS_SORTED_BY_NAME + type + " LIMIT 10 OFFSET ?";
                case "sortDate":
                    return GET_RECEIPTS_SORTED_BY_DATE + type + " LIMIT 10 OFFSET ?";
                case "sortStatus":
                    return GET_RECEIPTS_SORTED_BY_STATUS + type + " LIMIT 10 OFFSET ?";
                case "sortAccount":
                    return GET_RECEIPTS_SORTED_BY_ACCOUNT + type + " LIMIT 10 OFFSET ?";
                case "sortPurpose":
                    return GET_RECEIPTS_SORTED_BY_PURPOSE + type + " LIMIT 10 OFFSET ?";
            }
        }
        return GET_RECEIPTS;
    }


    private static List<Receipt> getReceipts(HttpServletRequest req, HttpSession session, User user, String query) {
        session.removeAttribute("query");
        session.setAttribute("query", query);
        Object pageNumberStr = session.getAttribute("recPage");
        int pageNumber;
        if (Objects.nonNull(req.getParameter("page"))) {
            pageNumber = Integer.parseInt(req.getParameter("page"));
            session.removeAttribute("recPage");
            session.setAttribute("recPage", pageNumber);
        } else if (Objects.isNull(pageNumberStr)) {
            pageNumber = 1;
            session.setAttribute("recPage", pageNumber);
        } else {
            pageNumber = Integer.parseInt(session.getAttribute("recPage").toString());
        }
        return ReceiptDAO.getUsersReceiptsWithPagination(user.getId(), pageNumber, query);
    }
}
