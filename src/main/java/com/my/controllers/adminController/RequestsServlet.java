package com.my.controllers.adminController;

import com.my.dto.RequestDTO;
import com.my.dao.AccountDAO;
import com.my.dao.RequestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/adm/requests")
public class RequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RequestsServlet#doGet");
        HttpSession session = req.getSession();
        if(Objects.nonNull(req.getParameter("action")) && req.getParameter("action").equals("unblock")){
            AccountDAO.unblockAccount(Integer.parseInt(req.getParameter("id")));
            RequestDAO.acceptRequest(Integer.parseInt(req.getParameter("id")));
        }
        int listLength = RequestDAO.getCountOfRequest();
        int pagesCount = listLength % 10 == 0 ? listLength / 10 : listLength / 10 + 1;
        req.setAttribute("pagesCount", pagesCount);
        Object pageNumberStr = session.getAttribute("reqPage");
        int pageNumber;
        if (Objects.nonNull(req.getParameter("page"))) {
            pageNumber = Integer.parseInt(req.getParameter("page"));
            session.removeAttribute("reqPage");
            session.setAttribute("reqPage", pageNumber);
        } else if (Objects.isNull(pageNumberStr)) {
            pageNumber = 1;
            session.setAttribute("reqPage", pageNumber);
        } else {
            pageNumber = Integer.parseInt(session.getAttribute("reqPage").toString());
        }

        List<RequestDTO> list = RequestDAO.getRequests(pageNumber);
        req.setAttribute("requests", list);
        req.getRequestDispatcher("/views/jsp/requests.jsp").forward(req, resp);
    }
}
