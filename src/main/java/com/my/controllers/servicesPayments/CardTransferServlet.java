package com.my.controllers.servicesPayments;

import com.my.dao.ReceiptDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cardTransfer")
public class CardTransferServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CardTransferServlet#doGet");
        HttpSession session = req.getSession();
        session.removeAttribute("purposeId");
        session.setAttribute("purposeId", 3);
        req.getRequestDispatcher("/views/jsp/options/cardTransfer.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CardTransferServlet#doPost");
        HttpSession session = req.getSession();
        String cardNumber = req.getParameter("card");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        int purposeId = Integer.parseInt(session.getAttribute("purposeId").toString());
        double amount = Double.parseDouble(req.getParameter("amount"));
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        int serviceId = ReceiptDAO.createNewEntryInTransService(cardNumber, firstName, lastName);
        ReceiptDAO.createEntryInReceipt(accountId, purposeId, amount, serviceId);
        resp.sendRedirect("/epamProject/mainPage");
    }
}
