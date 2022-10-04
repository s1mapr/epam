package com.my.controllers;

import com.my.dao.AccountDAO;
import com.my.entities.Account;
import com.my.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ProfileServlet#doGet");
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        req.removeAttribute("accounts");
        List<Account> list = AccountDAO.getUserAccounts(user.getId());
        if(!list.isEmpty()){
            req.setAttribute("accounts", list);
        }
        req.getRequestDispatcher("/views/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ProfileServlet#doPost");
    }
}
