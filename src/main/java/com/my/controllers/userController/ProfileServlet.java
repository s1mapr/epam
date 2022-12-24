package com.my.controllers.userController;

import com.my.dto.AccountDTO;
import com.my.dto.UserDTO;
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
/**
 * Controller for profile
 */
@WebServlet(USER_PROFILE_PATH)
public class ProfileServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        int listLength = AccountService.getUserAccountsListLength(user.getId());
        int pagesCount = listLength % 8 == 0 ? listLength / 8 : listLength / 8 + 1;
        req.setAttribute("pagesCount", pagesCount);
        AccountService.changeAccountStatusUser(req.getParameter("action"), req.getParameter("id"));
        String oldQuery = (String) session.getAttribute("profileQuery");
        int page = AccountService.getPageNumber(req.getParameter("page"), (Integer)session.getAttribute("profPage"));
        String query = AccountService.getQueryForUserAccounts(req.getParameter("sortAction"), req.getParameter("type"), oldQuery);
        List<AccountDTO> list = AccountService.getUserAccountsWithPagination(user.getId(), page, query);
        List<AccountDTO> accountList = AccountService.getUserAccountsWithoutPagination(user.getId());
        int notBlockedAccountCount = (int) accountList.stream().filter(x -> x.getStatus().equals("unblocked")).count();
        session.setAttribute("accLength", notBlockedAccountCount);
        session.setAttribute("accounts", accountList);
        session.setAttribute("profPage", page);
        session.setAttribute("profileQuery", query);
        req.setAttribute("accountsPag", list);
        req.getRequestDispatcher("/views/jsp/profile.jsp").forward(req, resp);
    }
}
