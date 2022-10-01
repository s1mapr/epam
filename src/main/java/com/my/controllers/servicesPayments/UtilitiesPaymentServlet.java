package com.my.controllers.servicesPayments;

import com.my.dao.ReceiptDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/utilitiesPayment")
public class UtilitiesPaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UtilitiesPaymentServlet#doGet");
        HttpSession session = req.getSession();
        session.removeAttribute("purposeId");
        session.setAttribute("purposeId", 4);
        req.getRequestDispatcher("/views/jsp/options/utilitiesPayment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UtilitiesPaymentServlet#doPost");
        int meter_w, meter_e, meter_g;
        double amount_w, amount_e, amount_g;
        if((req.getParameter("meter_w").length() == 0)){
            meter_w = 0;
        }else{
            meter_w = Integer.parseInt(req.getParameter("meter_w"));
        }
        if((req.getParameter("meter_e").length() == 0)){
            meter_e = 0;
        }else{
            meter_e = Integer.parseInt(req.getParameter("meter_e"));
        }
        if((req.getParameter("meter_g").length() == 0)){
            meter_g = 0;
        }else{
            meter_g = Integer.parseInt(req.getParameter("meter_g"));
        }

        if(req.getParameter("amount_w").length() == 0){
            amount_w = 0;
        }else{
            amount_w = Double.parseDouble(req.getParameter("amount_w"));
        }
        if(req.getParameter("amount_e").length() == 0){
            amount_e = 0;
        }else{
            amount_e = Double.parseDouble(req.getParameter("amount_e"));
        }
        if(req.getParameter("amount_g").length() == 0){
            amount_g = 0;
        }else{
            amount_g = Double.parseDouble(req.getParameter("amount_g"));
        }
        HttpSession session = req.getSession();
        int purposeId = Integer.parseInt(session.getAttribute("purposeId").toString());
        double amount = amount_w+amount_e+amount_g;
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        int serviceId = ReceiptDAO.createNewEntryInUtilitiesService(meter_w, meter_e, meter_g, amount_w, amount_e, amount_g);
        ReceiptDAO.createEntryInReceipt(accountId, purposeId, amount, serviceId);
        resp.sendRedirect("/epamProject/mainPage");

    }
}
