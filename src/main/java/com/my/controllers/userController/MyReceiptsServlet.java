package com.my.controllers.userController;

import com.my.dto.ReceiptDTO;
import com.my.dto.UserDTO;
import com.my.service.ReceiptService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.my.utils.HttpConstants.*;
/**
 * Controller for receipts
 */
@WebServlet(USER_RECEIPTS_PATH)
public class MyReceiptsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        ReceiptService.updateStatus();
        int listLength = ReceiptService.getReceiptListLength(user.getId());
        int pagesCount = listLength % 10 == 0 ? listLength / 10 : listLength / 10 + 1;
        req.setAttribute("pagesCount", pagesCount);
        String oldQuery = (String)session.getAttribute("query");
        int page = ReceiptService.getPageNumber(req.getParameter("page"), (Integer)session.getAttribute("recPage"));
        String query = ReceiptService.getQuery(req.getParameter("sortAction"), req.getParameter("type"), oldQuery);
        List<ReceiptDTO> list = ReceiptService.getReceiptsWithPagination(user.getId(), page, query);
        session.setAttribute("recPage", page);
        session.setAttribute("query", query);
        req.setAttribute("list", list);
        req.getRequestDispatcher("/views/jsp/myReceipts.jsp").forward(req, resp);
    }
}
