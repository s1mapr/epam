package com.my.controllers.userController.servicesPayments;

import com.my.dao.AccountDAO;
import com.my.dao.CardDAO;
import com.my.dao.ReceiptDAO;
import com.my.entities.User;
import com.my.utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

import static com.my.utils.HttpConstants.*;

@WebServlet(USER_UTILITIES_PAYMENT_PATH)
public class UtilitiesPaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UtilitiesPaymentServlet#doGet");
        HttpSession session = req.getSession();
        Validation validation = (Validation) session.getAttribute("valid");
        session.removeAttribute("valid");
        req.setAttribute("valid", validation);
        if (Objects.nonNull(session.getAttribute("notEnoughMoney"))) {
            session.removeAttribute("notEnoughMoney");
            req.setAttribute("notEnoughMoney", "Недостатньо грошей для операції");
        }
        session.removeAttribute("purposeId");
        session.setAttribute("purposeId", 4);
        req.getRequestDispatcher("/views/jsp/options/utilitiesPayment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UtilitiesPaymentServlet#doPost");
        HttpSession session = req.getSession();
        Validation validation = new Validation();
        boolean isValid = validation.utilitiesPaymentValidation(req.getParameter("meter_w"),
                req.getParameter("meter_e"), req.getParameter("meter_g"), req.getParameter("amount_w"),
                req.getParameter("amount_e"), req.getParameter("amount_g"));
        if (!isValid) {
            session.setAttribute("valid", validation);
            resp.sendRedirect(MAIN_SERVLET_PATH + USER_UTILITIES_PAYMENT_PATH);
            return;
        }
        int meter_w = Integer.parseInt(req.getParameter("meter_w"));
        int meter_e = Integer.parseInt(req.getParameter("meter_e"));
        int meter_g = Integer.parseInt(req.getParameter("meter_g"));
        double amount_w = Double.parseDouble(req.getParameter("amount_w"));
        double amount_e = Double.parseDouble(req.getParameter("amount_e"));
        double amount_g = Double.parseDouble(req.getParameter("amount_g"));

        User user = (User) session.getAttribute("user");
        int purposeId = Integer.parseInt(session.getAttribute("purposeId").toString());
        double amount = amount_w + amount_e + amount_g;
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        int cardId = AccountDAO.getCardId(accountId);
        double oldAmount = CardDAO.getAmount(cardId);
        double newAmount = oldAmount - amount;
        if (newAmount < 0) {
            session.setAttribute("notEnoughMoney", "Недостатньо грошей для операції");
            resp.sendRedirect(MAIN_SERVLET_PATH + USER_UTILITIES_PAYMENT_PATH);
            return;
        }

        int serviceId = ReceiptDAO.createNewEntryInUtilitiesService(meter_w, meter_e, meter_g, amount_w, amount_e, amount_g);
        ReceiptDAO.createEntryInReceipt(accountId, purposeId, amount, serviceId, user.getId());

        CardDAO.updateAmount(newAmount, cardId);
        user.setPaymentsCount(user.getPaymentsCount() + 1);
        resp.sendRedirect(MAIN_SERVLET_PATH + MAIN_PAGE_PATH);
    }
}
