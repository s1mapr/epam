package com.my.controllers;

import com.my.service.UserService;
import com.my.utils.Validation;
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
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        HttpSession session = req.getSession();
        if (UserService.getUser(login)) {
            Validation validation = new Validation();
            boolean isValid = validation.registrationValidation(login, password, firstName, lastName, email,phoneNumber);
            if (!isValid) {
                session.setAttribute("valid", validation);
                resp.sendRedirect(MAIN_SERVLET_PATH + REGISTRATION_PATH);
                return;
            }
            UserService.registration(login, password, firstName, lastName, email,phoneNumber);
            resp.sendRedirect(MAIN_SERVLET_PATH + AUTHORIZATION_PATH);
        } else {
            session.setAttribute("regError", "msg");
            resp.sendRedirect(MAIN_SERVLET_PATH + REGISTRATION_PATH);
        }
    }
}
