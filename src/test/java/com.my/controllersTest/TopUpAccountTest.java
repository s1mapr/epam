package com.my.controllersTest;

import com.my.controllers.userController.TopUpAccountServlet;
import com.my.service.AccountService;
import com.my.service.CardService;
import com.my.utils.Validation;
import org.mockito.MockedStatic;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.my.utils.HttpConstants.MAIN_SERVLET_PATH;
import static com.my.utils.HttpConstants.USER_PROFILE_PATH;
import static org.mockito.Mockito.*;

public class TopUpAccountTest {
    @Test
    public void doPostTest() throws ServletException, IOException {
        final Validation validation = mock(Validation.class);
        final TopUpAccountServlet servlet = new TopUpAccountServlet();
        MockedStatic<AccountService> mockedSettings;
        MockedStatic<CardService> mockedSettings1;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        mockedSettings = mockStatic(AccountService.class);
        mockedSettings1 = mockStatic((CardService.class));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("accountId")).thenReturn("9");
        when(request.getParameter("cardNumber")).thenReturn("7896541258996322");
        when(request.getParameter("expiryDate")).thenReturn("01/21");
        when(request.getParameter("cvv")).thenReturn("789");
        when(request.getParameter("amount")).thenReturn("11");
        when(request.getParameter("cardHolder")).thenReturn("Maksym Prokopenko");
        when(validation.topUpValidation("7896541258996322", "01/21", "789", "11", "Maksym Prokopenko")).thenReturn(false);
        mockedSettings.when(() -> AccountService.getCardId(9)).thenReturn(10);
        mockedSettings1.when(() -> CardService.getAmount(10)).thenReturn(15.5);
        servlet.doPost(request, response);
        verify(response).sendRedirect(MAIN_SERVLET_PATH + USER_PROFILE_PATH);
        mockedSettings.close();
        mockedSettings1.close();
    }

    @Test
    public void doGetTest() throws ServletException, IOException {
        final Validation validation = new Validation();
        final TopUpAccountServlet servlet = new TopUpAccountServlet();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("valid")).thenReturn(validation);
        when(session.getAttribute("accountId")).thenReturn("132");
        when(request.getRequestDispatcher("/views/jsp/topUpAccount.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(session, times(1)).removeAttribute("valid");
        verify(request, times(1)).setAttribute("valid", validation);
        verify(dispatcher).forward(request, response);
    }
}
