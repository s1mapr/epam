package com.my.controllers.adminController;

import com.my.dto.UserDTO;
import com.my.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.my.utils.HttpConstants.*;

@WebServlet(ADMIN_USERS_PATH)
public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int listLength = UserService.getListLength();
        int pagesCount = listLength % 10 == 0 ? listLength / 10 : listLength / 10 + 1;
        req.setAttribute("pagesCount", pagesCount);
        UserService.changeUserStatus(req.getParameter("action"), req.getParameter("id"));
        String oldQuery = (String)session.getAttribute("usersQuery");
        int page = UserService.getPageNumber(req.getParameter("page"), (Integer)session.getAttribute("userPage"));
        String query = UserService.getQuery(req.getParameter("sortAction"), req.getParameter("type"), oldQuery);
        List<UserDTO> users = UserService.getAllUsersWithPagination(page, query);
        session.setAttribute("userPage", page);
        session.setAttribute("usersQuery", query);
        req.setAttribute("list", users);
        req.getRequestDispatcher("/views/jsp/users.jsp").forward(req, resp);
    }
}