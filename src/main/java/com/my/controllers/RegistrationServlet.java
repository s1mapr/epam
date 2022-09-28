package com.my.controllers;

import com.my.dao.DBManager;
import com.my.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RegistrationServlet#doGet");
        req.getRequestDispatcher("views/jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RegistrationServlet#doPost");
        String login = req.getParameter("login");
        HttpSession session = req.getSession();
        if(UserDAO.checkUserForRegistration(login)){
            UserDAO.registration(req.getParameter("login"),
                    req.getParameter("password"),
                    req.getParameter("firstName"),
                    req.getParameter("lastName"),
                    req.getParameter("email"),
                    req.getParameter("phoneNumber"));

            resp.sendRedirect("/epamProject/authorization");
        }
        else{
            session.setAttribute("isOk", "user with same login already exists");
            resp.sendRedirect("/epamProject/registration");
        }
    }
}
