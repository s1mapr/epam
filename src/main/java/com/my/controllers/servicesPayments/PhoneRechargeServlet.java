package com.my.controllers.servicesPayments;

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

@WebServlet("/phoneRecharge")
public class PhoneRechargeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PhoneRecharge#doGet");
        HttpSession session = req.getSession();
        Validation validation = (Validation) session.getAttribute("valid");
        session.removeAttribute("valid");
        req.setAttribute("valid", validation);
        if(Objects.nonNull(session.getAttribute("notEnoughMoney"))){
            session.removeAttribute("notEnoughMoney");
            req.setAttribute("notEnoughMoney", "Недостатньо грошей для операції");
        }
        session.removeAttribute("purposeId");
        session.setAttribute("purposeId", 1);
        req.getRequestDispatcher("/views/jsp/options/phoneRecharge.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PhoneRecharge#doPost");
        HttpSession session = req.getSession();
        Validation validation = new Validation();
        boolean isValid = validation.phoneRechargeValidation(req.getParameter("phone"), req.getParameter("amount"));
        if(!isValid){
            session.setAttribute("valid", validation);
            resp.sendRedirect("/epamProject/phoneRecharge");
            return;
        }
        User user = (User) session.getAttribute("user");
        String number = req.getParameter("phone");
        int purposeId = Integer.parseInt(session.getAttribute("purposeId").toString());
        double amount = Double.parseDouble(req.getParameter("amount"));
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        int cardId = AccountDAO.getCardId(accountId);
        double oldAmount = CardDAO.getAmount(cardId);
        double newAmount = oldAmount - amount;

        if(newAmount<0){
            session.setAttribute("notEnoughMoney", "Недостатньо грошей для операції");
            resp.sendRedirect("/epamProject/phoneRecharge");
            return;
        }
        int serviceId = ReceiptDAO.createNewEntryInPhoneService(number);
        ReceiptDAO.createEntryInReceipt(accountId, purposeId,amount, serviceId, user.getId());

        CardDAO.updateAmount(newAmount, cardId);
        resp.sendRedirect("/epamProject/mainPage");
    }
}
