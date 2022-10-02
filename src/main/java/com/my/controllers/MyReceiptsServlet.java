package com.my.controllers;

import com.my.dao.ReceiptDAO;
import com.my.entities.Receipt;
import com.my.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/myReceipts")
public class MyReceiptsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MyReceiptsServlet#doGet");
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        List<Receipt> list = ReceiptDAO.getUsersReceipts(user.getId());
        req.setAttribute("list", list);
        req.getRequestDispatcher("/views/jsp/myReceipts.jsp").forward(req, resp);
    }
}
