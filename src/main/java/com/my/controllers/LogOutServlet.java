package com.my.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.my.utils.HttpConstants.*;

@WebServlet(LOGOUT_PATH)
public class LogOutServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LogOutServlet#doGet");
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(MAIN_SERVLET_PATH+MAIN_PAGE_PATH);
    }
}
