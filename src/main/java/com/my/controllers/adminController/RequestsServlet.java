package com.my.controllers.adminController;

import com.my.dto.RequestDTO;
import com.my.service.RequestService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import static com.my.utils.HttpConstants.*;

@WebServlet(ADMIN_REQUESTS_PATH)
public class RequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int listLength = RequestService.getListLength();
        int pagesCount = listLength % 10 == 0 ? listLength / 10 : listLength / 10 + 1;
        req.setAttribute("pagesCount", pagesCount);
        RequestService.unblockUserAccount(req.getParameter("action"), req.getParameter("id"));
        int page = RequestService.getPageNumber(req.getParameter("page"), session.getAttribute("reqPage"));
        List<RequestDTO> list = RequestService.getRequests(page);
        session.setAttribute("reqPage", page);
        req.setAttribute("requests", list);
        req.getRequestDispatcher("/views/jsp/requests.jsp").forward(req, resp);
    }
}
