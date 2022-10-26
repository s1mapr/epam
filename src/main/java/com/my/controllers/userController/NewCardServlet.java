package com.my.controllers.userController;

import com.my.dto.UserDTO;
import com.my.service.AccountService;
import com.my.service.CardService;
import com.my.utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

import static com.my.utils.HttpConstants.*;

@WebServlet(USER_NEW_CARD_PATH)
public class NewCardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(Objects.nonNull(session.getAttribute("accountExist"))){
            session.removeAttribute("accountExist");
            req.setAttribute("accountExist", "msg");
        }
        if(Objects.nonNull(session.getAttribute("cardExist"))){
            session.removeAttribute("cardExist");
            req.setAttribute("cardExist", "msg");
        }
        Validation validation = (Validation) session.getAttribute("valid");
        session.removeAttribute("valid");
        req.setAttribute("valid", validation);
        req.getRequestDispatcher("/views/jsp/newAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        String name = req.getParameter("name");
        String cardNumber = req.getParameter("card");
        String date = req.getParameter("date");
        String cvv = req.getParameter("cvv");
        if(AccountService.checkAccountNameIfExist(name, user.getId())){
            session.setAttribute("accountExist", "msg");
            resp.sendRedirect(MAIN_SERVLET_PATH + USER_NEW_CARD_PATH);
            return;
        }
        if (!CardService.checkCardNumberIfExist(cardNumber)) {
            session.setAttribute("cardExist", "msg");
            resp.sendRedirect(MAIN_SERVLET_PATH + USER_NEW_CARD_PATH);
            return;
        }
        Validation validation = new Validation();
        boolean isValid = validation.newCardValidation(name, cardNumber, date, cvv, req.getParameter("cardHolder"));
        if (!isValid) {
            session.setAttribute("valid", validation);
            resp.sendRedirect(MAIN_SERVLET_PATH + USER_NEW_CARD_PATH);
            return;
        }
        int cardId = CardService.createCard(cardNumber, date, cvv);
        AccountService.createNewAccount(name, cardId, user.getId());
        user.setAccountsCount(user.getAccountsCount() + 1);
        resp.sendRedirect(MAIN_SERVLET_PATH + USER_PROFILE_PATH);
    }
}
