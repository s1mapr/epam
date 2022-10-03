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

@WebServlet("/cardTransfer")
public class CardTransferServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CardTransferServlet#doGet");
        HttpSession session = req.getSession();
        Validation validation = (Validation) session.getAttribute("valid");
        session.removeAttribute("valid");
        req.setAttribute("valid", validation);
        if(Objects.nonNull(session.getAttribute("notEnoughMoney"))){
            session.removeAttribute("notEnoughMoney");
            req.setAttribute("notEnoughMoney", "Недостатньо грошей для операції");
        }
        session.removeAttribute("purposeId");
        session.setAttribute("purposeId", 3);
        req.getRequestDispatcher("/views/jsp/options/cardTransfer.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CardTransferServlet#doPost");
        HttpSession session = req.getSession();
        Validation validation = new Validation();
        boolean isValid = validation.cardTransferValidation(req.getParameter("card"),
                req.getParameter("firstName"), req.getParameter("lastName"), req.getParameter("amount"));
        if(!isValid){
            session.setAttribute("valid", validation);
            resp.sendRedirect("/epamProject/cardTransfer");
            return;
        }
        User user = (User) session.getAttribute("user");
        String cardNumber = req.getParameter("card");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        int purposeId = Integer.parseInt(session.getAttribute("purposeId").toString());
        double amount = Double.parseDouble(req.getParameter("amount"));
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        int cardId = AccountDAO.getCardId(accountId);
        double oldAmount = CardDAO.getAmount(cardId);
        double newAmount = oldAmount - amount;
        if(newAmount<0){
            session.setAttribute("notEnoughMoney", "Недостатньо грошей для операції");
            resp.sendRedirect("/epamProject/cardTransfer");
            return;
        }

        int serviceId = ReceiptDAO.createNewEntryInTransService(cardNumber, firstName, lastName);
        ReceiptDAO.createEntryInReceipt(accountId, purposeId, amount, serviceId, user.getId());
        CardDAO.updateAmount(newAmount, cardId);
        resp.sendRedirect("/epamProject/mainPage");
    }
}
