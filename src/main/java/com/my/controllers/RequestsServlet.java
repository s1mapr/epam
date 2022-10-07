package com.my.controllers;

import com.my.controllers.dto.RequestDTO;
import com.my.dao.AccountDAO;
import com.my.dao.ReceiptDAO;
import com.my.dao.RequestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/requests")
public class RequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RequestsServlet#doGet");

        if(Objects.nonNull(req.getParameter("action")) && req.getParameter("action").equals("unblock")){
            AccountDAO.unblockAccount(Integer.parseInt(req.getParameter("id")));
            RequestDAO.acceptRequest(Integer.parseInt(req.getParameter("id")));
        }
        List<RequestDTO> list = RequestDAO.getRequests();
        req.setAttribute("requests", list);
        req.getRequestDispatcher("/views/jsp/requests.jsp").forward(req, resp);
    }
}
