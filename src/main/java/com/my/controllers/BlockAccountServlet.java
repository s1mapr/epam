package com.my.controllers;

import com.my.dao.AccountDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/blockAccount")
public class BlockAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("BlockAccountServlet#doGet");
        int accountId = Integer.parseInt(req.getParameter("id"));
        AccountDAO.blockAccount(accountId);
        req.getRequestDispatcher("/views/jsp/profile.jsp").forward(req,resp);
    }
}
