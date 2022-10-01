package com.my.controllers.servicesPayments;

import com.my.dao.ReceiptDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/finesPayment")
public class FinesPaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("finesPaymentServlet#doGet");
        HttpSession session = req.getSession();
        session.removeAttribute("purposeId");
        session.setAttribute("purposeId", 5);
        req.getRequestDispatcher("/views/jsp/options/finesPayment.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("finesPaymentServlet#doPost");
        HttpSession session = req.getSession();
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String patronymic = req.getParameter("patronymic");
        String fineNumber = req.getParameter("number");
        int purposeId = Integer.parseInt(session.getAttribute("purposeId").toString());
        double amount = Double.parseDouble(req.getParameter("amount"));
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        int serviceId = ReceiptDAO.createNewEntryInFinesService(firstName, lastName, patronymic, fineNumber);
        ReceiptDAO.createEntryInReceipt(accountId, purposeId, amount, serviceId);
        resp.sendRedirect("/epamProject/mainPage");

    }
}
