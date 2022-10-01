package com.my.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/phoneRecharge")
public class PhoneRecharge extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PhoneRecharge#doGet");
        req.removeAttribute("purposeId");
        req.setAttribute("purposeId", 1);
        req.getRequestDispatcher("/views/jsp/options/phoneRecharge.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PhoneRecharge#doPost");
    }
}
