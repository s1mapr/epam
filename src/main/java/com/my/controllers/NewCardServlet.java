package com.my.controllers;

import com.my.dao.AccountDAO;
import com.my.dao.CardDAO;
import com.my.dao.UserDAO;
import com.my.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/addNewAccount")
public class NewCardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("NewCardServlet#doGet");
        req.getRequestDispatcher("/views/jsp/newAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("NewCardServlet#doPost");
        String cardNumber = req.getParameter("card");
        HttpSession session = req.getSession();
        if(CardDAO.checkCardNumber(cardNumber)){
            User user = (User)session.getAttribute("user");
            int cardId = CardDAO.createNewCard(cardNumber,
                    req.getParameter("date"),
                    req.getParameter("cvv"));
            AccountDAO.addNewAccount(req.getParameter("name"), cardId, user.getId());
            resp.sendRedirect("/epamProject/profile");
        }
        else{
            resp.sendRedirect("/epamProject/addNewAccount");
        }
    }
}
