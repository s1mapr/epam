package com.my.controllers;

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

@WebServlet("/myReceipts")
public class MyReceiptsServlet extends HttpServlet {
    public static String GET_RECEIPTS = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_AMOUNT_ASC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY amount ASC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_AMOUNT_DESC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY amount DESC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_NAME_ASC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY receipt.name ASC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_NAME_DESC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY receipt.name DESC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_DATE_ASC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY date ASC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_DATE_DESC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY date DESC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_STATUS_ASC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY receipt.status ASC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_STATUS_DESC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY receipt.status DESC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_PURPOSE_ASC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY purpose.name ASC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_PURPOSE_DESC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY purpose.name DESC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_ACCOUNT_ASC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY account.name ASC LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_ACCOUNT_DESC = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY account.name DESC LIMIT 10 OFFSET ?";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MyReceiptsServlet#doGet");
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        ReceiptDAO.updateStatus();
        int listLength = ReceiptDAO.getPaymentsCountOfUser(user.getId());
        int pagesCount = listLength%10==0?listLength/10:listLength/10+1;
        req.setAttribute("pagesCount", pagesCount);
        List<Receipt> list;

        if(Objects.nonNull(session.getAttribute("query"))&&Objects.isNull(req.getParameter("action"))){
            list = getReceipts(req, session, user, session.getAttribute("query").toString());
        }else{
            String query = getQuery(req);
            list = getReceipts(req, session, user, query);
        }
        req.setAttribute("list", list);
        req.getRequestDispatcher("/views/jsp/myReceipts.jsp").forward(req, resp);
    }

    private static String getQuery(HttpServletRequest req){
        HttpSession session = req.getSession();
        String action = req.getParameter("action");
        if(Objects.nonNull(action)){
            switch (action){
                case "sortAmount":
                    int amountSortId = Integer.parseInt(req.getParameter("sortId"));
                    if(amountSortId ==1) {
                        session.removeAttribute("amountSortId");
                        session.setAttribute("amountSortId", 2);
                        return GET_RECEIPTS_SORTED_BY_AMOUNT_ASC;
                    }
                    session.removeAttribute("amountSortId");
                    session.setAttribute("amountSortId", 1);
                    return GET_RECEIPTS_SORTED_BY_AMOUNT_DESC;
                case "sortName":
                    int nameSortId = Integer.parseInt(req.getParameter("sortId"));
                    if(nameSortId ==1) {
                        session.removeAttribute("nameSortId");
                        session.setAttribute("nameSortId", 2);
                        return GET_RECEIPTS_SORTED_BY_NAME_ASC;
                    }
                    session.removeAttribute("nameSortId");
                    session.setAttribute("nameSortId", 1);
                    return GET_RECEIPTS_SORTED_BY_NAME_DESC;
                case "sortDate":
                    int dateSortId = Integer.parseInt(req.getParameter("sortId"));
                    if(dateSortId ==1) {
                        session.removeAttribute("dateSortId");
                        session.setAttribute("dateSortId", 2);
                        return GET_RECEIPTS_SORTED_BY_DATE_ASC;
                    }
                    session.removeAttribute("dateSortId");
                    session.setAttribute("dateSortId", 1);
                    return GET_RECEIPTS_SORTED_BY_DATE_DESC;
                case "sortStatus":
                    int statusSortId = Integer.parseInt(req.getParameter("sortId"));
                    if(statusSortId ==1) {
                        session.removeAttribute("statusSortId");
                        session.setAttribute("statusSortId", 2);
                        return GET_RECEIPTS_SORTED_BY_STATUS_ASC;
                    }
                    session.removeAttribute("statusSortId");
                    session.setAttribute("statusSortId", 1);
                    return GET_RECEIPTS_SORTED_BY_STATUS_DESC;
                case "sortAccount":
                    int accountSortId = Integer.parseInt(req.getParameter("sortId"));
                    if(accountSortId ==1) {
                        session.removeAttribute("accountSortId");
                        session.setAttribute("accountSortId", 2);
                        return GET_RECEIPTS_SORTED_BY_ACCOUNT_ASC;
                    }
                    session.removeAttribute("accountSortId");
                    session.setAttribute("accountSortId", 1);
                    return GET_RECEIPTS_SORTED_BY_ACCOUNT_DESC;
                case "sortPurpose":
                    int purposeSortId = Integer.parseInt(req.getParameter("sortId"));
                    if(purposeSortId ==1) {
                        session.removeAttribute("purposeSortId");
                        session.setAttribute("purposeSortId", 2);
                        return GET_RECEIPTS_SORTED_BY_PURPOSE_ASC;
                    }
                    session.removeAttribute("purposeSortId");
                    session.setAttribute("purposeSortId", 1);
                    return GET_RECEIPTS_SORTED_BY_PURPOSE_DESC;
            }
        }
        session.setAttribute("amountSortId", 1);
        session.setAttribute("nameSortId", 1);
        session.setAttribute("dateSortId", 1);
        session.setAttribute("statusSortId", 1);
        session.setAttribute("purposeSortId", 1);
        session.setAttribute("accountSortId", 1);
        return GET_RECEIPTS;
    }




    private static List<Receipt> getReceipts(HttpServletRequest req, HttpSession session, User user, String query) {
        session.removeAttribute("query");
        session.setAttribute("query", query);

        Object pageNumberStr = session.getAttribute("recPage");
        int pageNumber;
        if(Objects.nonNull(req.getParameter("page"))){
            pageNumber = Integer.parseInt(req.getParameter("page"));
            session.removeAttribute("recPage");
            session.setAttribute("recPage", pageNumber);
        }else if(Objects.isNull(pageNumberStr)){
            pageNumber = 1;
            session.setAttribute("recPage", pageNumber);
        }
        else{
            pageNumber = Integer.parseInt(session.getAttribute("recPage").toString());
        }
        return ReceiptDAO.getUsersReceiptsWithPagination(user.getId(), pageNumber, query);
    }
}
