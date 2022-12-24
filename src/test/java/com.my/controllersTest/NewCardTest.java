package com.my.controllersTest;

import com.my.controllers.userController.NewCardServlet;
import com.my.dto.UserDTO;
import com.my.service.AccountService;
import com.my.service.CardService;
import com.my.utils.Validation;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.my.utils.HttpConstants.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class NewCardTest {

    @Test
    public void doGetTest() throws ServletException, IOException {
        final NewCardServlet servlet = new NewCardServlet();
        final Validation validation = mock(Validation.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("accountExist")).thenReturn(null);
        when(session.getAttribute("cardExist")).thenReturn(null);
        when(session.getAttribute("valid")).thenReturn(validation);
        when(request.getRequestDispatcher("/views/jsp/newAccount.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(session).removeAttribute("valid");
        verify(request).setAttribute("valid", validation);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        final Validation validation = mock(Validation.class);
        final NewCardServlet servlet = new NewCardServlet();
        final UserDTO user = mock(UserDTO.class);
        MockedStatic<AccountService> mockedAcc;
        MockedStatic<CardService> mockedCard;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        mockedAcc = mockStatic(AccountService.class);
        mockedCard = mockStatic(CardService.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("name")).thenReturn("accountName");
        when(request.getParameter("card")).thenReturn("9999666633335555");
        when(request.getParameter("date")).thenReturn("01/22");
        when(request.getParameter("cvv")).thenReturn("253");
        user.setId(3);
        mockedAcc.when(()->AccountService.checkAccountNameIfExist("accountName", user.getId())).thenReturn(false);
        mockedCard.when(()->CardService.checkCardNumberIfExist("9999666633335555")).thenReturn(true);
        when(request.getParameter("cardHolder")).thenReturn("Maksym Prokopenko");
        when(validation.newCardValidation("accountName", "9999666633335555", "01/22","253","Maksym Prokopenko" )).thenReturn(true);
        mockedCard.when(()->CardService.createCard("9999666633335555", "01/22","253")).thenReturn(1);
        user.setPaymentsCount(10);
        servlet.doPost(request, response);
        verify(user).setPaymentsCount(9 + 1);
        verify(response).sendRedirect(MAIN_SERVLET_PATH + USER_PROFILE_PATH);
        mockedAcc.close();
        mockedCard.close();
    }
}
