package com.my.controllers;

import com.my.dao.AccountDAO;
import com.my.entities.Account;
import com.my.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ProfileServlet#doGet");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        req.removeAttribute("accounts");
        int listLength = AccountDAO.getCountOfUsersAccounts(user.getId());
        int pagesCount = listLength % 5 == 0 ? listLength / 5 : listLength / 5 + 1;
        req.setAttribute("pagesCount", pagesCount);
        Object pageNumberStr = session.getAttribute("profPage");
        int pageNumber;
        if (Objects.nonNull(req.getParameter("page"))) {
            pageNumber = Integer.parseInt(req.getParameter("page"));
            session.removeAttribute("profPage");
            session.setAttribute("profPage", pageNumber);
        } else if (Objects.isNull(pageNumberStr)) {
            pageNumber = 1;
            session.setAttribute("profPage", pageNumber);
        } else {
            pageNumber = Integer.parseInt(session.getAttribute("profPage").toString());
        }

        List<Account> list = AccountDAO.getUserAccounts(user.getId(), pageNumber);
        req.setAttribute("accounts", list);
        req.getRequestDispatcher("/views/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ProfileServlet#doPost");
    }
}
