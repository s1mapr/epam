package com.my.controllers.userController.servicesPayments;

import com.my.dto.UserDTO;
import com.my.service.AccountService;
import com.my.service.CardService;
import com.my.service.ReceiptService;
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
        HttpSession session = req.getSession();
        Validation validation = (Validation) session.getAttribute("valid");
        session.removeAttribute("valid");
        req.setAttribute("valid", validation);
        if (Objects.nonNull(session.getAttribute("notEnoughMoney"))) {
            session.removeAttribute("notEnoughMoney");
            req.setAttribute("notEnoughMoney", "Недостатньо грошей для операції");
        }
        session.setAttribute("purposeId", 4);
        req.getRequestDispatcher("/views/jsp/options/utilitiesPayment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        UserDTO user = (UserDTO) session.getAttribute("user");
        int meterW = Integer.parseInt(req.getParameter("meter_w"));
        int meterE = Integer.parseInt(req.getParameter("meter_e"));
        int meterG = Integer.parseInt(req.getParameter("meter_g"));
        double amountW = Double.parseDouble(req.getParameter("amount_w"));
        double amountE = Double.parseDouble(req.getParameter("amount_e"));
        double amountG = Double.parseDouble(req.getParameter("amount_g"));
        int purposeId = Integer.parseInt(session.getAttribute("purposeId").toString());
        double amount = amountW + amountE + amountG;
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        int cardId = AccountService.getCardId(accountId);
        double oldAmount = CardService.getAmount(cardId);
        double newAmount = oldAmount - amount;
        if (newAmount < 0) {
            session.setAttribute("notEnoughMoney", "Недостатньо грошей для операції");
            resp.sendRedirect(MAIN_SERVLET_PATH + USER_UTILITIES_PAYMENT_PATH);
            return;
        }
        int serviceId = ReceiptService.createNewEntryInUtilitiesService(meterW, meterE, meterG, amountW, amountE, amountG);
        ReceiptService.createEntryInReceipt(accountId, purposeId, amount, serviceId, user.getId());
        CardService.updateAmount(newAmount, cardId);
        user.setPaymentsCount(user.getPaymentsCount() + 1);
        resp.sendRedirect(MAIN_SERVLET_PATH + USER_RECEIPTS_PATH);
    }
}
