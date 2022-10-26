package com.my.controllers.adminController;

import com.my.dto.AccountDTO;
import com.my.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.my.utils.HttpConstants.*;

@WebServlet(ADMIN_ACCOUNTS_PATH)
public class AccountsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int listLength = AccountService.getListLength();
        int pagesCount = listLength % 10 == 0 ? listLength / 10 : listLength / 10 + 1;
        req.setAttribute("pagesCount", pagesCount);
        AccountService.changeAccountStatusAdmin(req.getParameter("action"), req.getParameter("id"));
        String oldQuery = (String)session.getAttribute("accountQuery");
        int page = AccountService.getPageNumber(req.getParameter("page"), (Integer) session.getAttribute("accPage"));
        String query = AccountService.getQueryForAdminAccounts(req.getParameter("sortAction"), req.getParameter("type"), oldQuery);
        List<AccountDTO> accounts = AccountService.getAllAccountsWithPagination(page, query);
        session.setAttribute("accPage", page);
        session.setAttribute("accountQuery", query);
        req.setAttribute("list", accounts);
        req.getRequestDispatcher("/views/jsp/accounts.jsp").forward(req, resp);
    }
}
