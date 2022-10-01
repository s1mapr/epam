package com.my.controllers.servicesPayments;

import com.my.dao.ReceiptDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/servicesPayment")
public class ServicesPaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ServicesPaymentServlet#doGet");
        HttpSession session = req.getSession();
        session.removeAttribute("purposeId");
        session.setAttribute("purposeId", 2);
        req.getRequestDispatcher("/views/jsp/options/servicesPayment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ServicesPaymentServlet#doPost");
        HttpSession session = req.getSession();
        String cardNumber = req.getParameter("card");
        String serviceName = req.getParameter("name");
        int purposeId = Integer.parseInt(session.getAttribute("purposeId").toString());
        double amount = Double.parseDouble(req.getParameter("amount"));
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        int serviceId = ReceiptDAO.createNewEntryInServService(cardNumber, serviceName);
        ReceiptDAO.createEntryInReceipt(accountId, purposeId, amount, serviceId);
        resp.sendRedirect("/epamProject/mainPage");
    }
}
