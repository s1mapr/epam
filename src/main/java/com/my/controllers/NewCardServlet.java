package com.my.controllers;

import com.my.dao.AccountDAO;
import com.my.dao.CardDAO;
import com.my.entities.User;
import com.my.utils.Validation;

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
        HttpSession session = req.getSession();
        Validation validation = (Validation) session.getAttribute("valid");
        session.removeAttribute("valid");
        req.setAttribute("valid", validation);

        req.getRequestDispatcher("/views/jsp/newAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("NewCardServlet#doPost");
        HttpSession session = req.getSession();
        Validation validation = new Validation();
        boolean isValid = validation.newCardValidation(req.getParameter("name"),
                req.getParameter("card"), req.getParameter("date"), req.getParameter("cvv"));
        if(!isValid){
            session.setAttribute("valid", validation);
            resp.sendRedirect("/epamProject/addNewAccount");
            return;
        }
        String cardNumber = req.getParameter("card");
        if (!CardDAO.checkCardNumber(cardNumber)) {
            resp.sendRedirect("/epamProject/addNewAccount");
            return;
        }
        User user = (User) session.getAttribute("user");
        int cardId = CardDAO.createNewCard(cardNumber,
                req.getParameter("date"),
                req.getParameter("cvv"));
        AccountDAO.addNewAccount(req.getParameter("name"), cardId, user.getId());
        resp.sendRedirect("/epamProject/profile");
    }
}
