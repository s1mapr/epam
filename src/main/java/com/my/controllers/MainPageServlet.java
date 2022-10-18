package com.my.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.utils.HttpConstants.*;

@WebServlet(MAIN_PAGE_PATH)
public class MainPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MainPageServlet#doGet");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
