package com.my.controllers.servicesPayments;

import com.my.dao.AccountDAO;
import com.my.dao.CardDAO;
import com.my.dao.ReceiptDAO;
import com.my.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/finesPayment")
public class FinesPaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("finesPaymentServlet#doGet");
        HttpSession session = req.getSession();
        if(Objects.nonNull(session.getAttribute("notEnoughMoney"))){
            session.removeAttribute("notEnoughMoney");
            req.setAttribute("notEnoughMoney", "Недостатньо грошей для операції");
        }
        session.removeAttribute("purposeId");
        session.setAttribute("purposeId", 5);
        req.getRequestDispatcher("/views/jsp/options/finesPayment.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("finesPaymentServlet#doPost");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String patronymic = req.getParameter("patronymic");
        String fineNumber = req.getParameter("number");
        int purposeId = Integer.parseInt(session.getAttribute("purposeId").toString());
        double amount = Double.parseDouble(req.getParameter("amount"));
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        int cardId = AccountDAO.getCardId(accountId);
        double oldAmount = CardDAO.getAmount(cardId);
        double newAmount = oldAmount - amount;
        if(newAmount<0){
            session.setAttribute("notEnoughMoney", "Недостатньо грошей для операції");
            resp.sendRedirect("/epamProject/finesPayment");
            return;
        }
        int serviceId = ReceiptDAO.createNewEntryInFinesService(firstName, lastName, patronymic, fineNumber);
        ReceiptDAO.createEntryInReceipt(accountId, purposeId, amount, serviceId, user.getId());

        CardDAO.updateAmount(newAmount, cardId);
        resp.sendRedirect("/epamProject/mainPage");


    }
}
