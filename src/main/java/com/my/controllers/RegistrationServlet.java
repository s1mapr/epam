package com.my.controllers;

import com.my.dao.DBManager;
import com.my.dao.UserDAO;
import com.my.utils.Validation;

import javax.print.attribute.standard.JobKOctets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

import static com.my.utils.HttpConstants.*;

@WebServlet(REGISTRATION_PATH)
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RegistrationServlet#doGet");
        HttpSession session = req.getSession();
        if(Objects.nonNull(session.getAttribute("regError"))){
            session.removeAttribute("regError");
            req.setAttribute("regError", "msg");
        }
        Validation validation = (Validation) session.getAttribute("valid");
        session.removeAttribute("valid");
        req.setAttribute("valid", validation);
        req.getRequestDispatcher("views/jsp/registration.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RegistrationServlet#doPost");
        String login = req.getParameter("login");
        HttpSession session = req.getSession();
        if (UserDAO.checkUserForRegistration(login)) {
            Validation validation = new Validation();
            boolean isValid = validation.registrationValidation(req.getParameter("login"),
                    req.getParameter("password"),
                    req.getParameter("firstName"),
                    req.getParameter("lastName"),
                    req.getParameter("email"),
                    req.getParameter("phoneNumber"));
            if (!isValid) {
                session.setAttribute("valid", validation);
                resp.sendRedirect(MAIN_SERVLET_PATH + REGISTRATION_PATH);
                return;
            }
            UserDAO.registration(req.getParameter("login"),
                    req.getParameter("password"),
                    req.getParameter("firstName"),
                    req.getParameter("lastName"),
                    req.getParameter("email"),
                    req.getParameter("phoneNumber"));

            resp.sendRedirect(MAIN_SERVLET_PATH + AUTHORIZATION_PATH);
        } else {
            session.setAttribute("regError", "msg");
            resp.sendRedirect(MAIN_SERVLET_PATH + REGISTRATION_PATH);
        }
    }
}
