package com.my.controllersTest;

import com.my.controllers.userController.servicesPayments.UtilitiesPaymentServlet;
import com.my.dto.UserDTO;
import com.my.service.AccountService;
import com.my.service.CardService;
import com.my.service.ReceiptService;
import com.my.utils.Validation;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.my.utils.HttpConstants.MAIN_SERVLET_PATH;
import static com.my.utils.HttpConstants.USER_RECEIPTS_PATH;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class UtilitiesPaymentTest {
    @Test
    public void doGetTest() throws ServletException, IOException {
        final UtilitiesPaymentServlet servlet = new UtilitiesPaymentServlet();
        final Validation validation = mock(Validation.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("valid")).thenReturn(validation);
        when(session.getAttribute("notEnoughMoney")).thenReturn(null);
        when(request.getRequestDispatcher("/views/jsp/options/utilitiesPayment.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(session).removeAttribute("valid");
        verify(request).setAttribute("valid", validation);
        verify(session).setAttribute("purposeId", 4);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        final Validation validation = mock(Validation.class);
        final UtilitiesPaymentServlet servlet = new UtilitiesPaymentServlet();
        final UserDTO user = mock(UserDTO.class);
        MockedStatic<AccountService> mockedAcc;
        MockedStatic<CardService> mockedCard;
        MockedStatic<ReceiptService> mockedRec;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        mockedAcc = mockStatic(AccountService.class);
        mockedCard = mockStatic(CardService.class);
        mockedRec = mockStatic(ReceiptService.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("meter_w")).thenReturn("123");
        when(request.getParameter("meter_e")).thenReturn("123");
        when(request.getParameter("meter_g")).thenReturn("123");
        when(request.getParameter("amount_w")).thenReturn("3");
        when(request.getParameter("amount_e")).thenReturn("3");
        when(request.getParameter("amount_g")).thenReturn("3");
        when(validation.utilitiesPaymentValidation("123", "123", "123", "3","3","3")).thenReturn(true);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("purposeId")).thenReturn(4);
        when(request.getParameter("accountId")).thenReturn("3");
        mockedAcc.when(()->AccountService.getCardId(3)).thenReturn(3);
        mockedCard.when(()->CardService.getAmount(3)).thenReturn(1322.0);
        mockedRec.when(()->ReceiptService.createNewEntryInUtilitiesService(123, 123, 123, 3,3,3)).thenReturn(12);
        user.setPaymentsCount(10);
        servlet.doPost(request, response);
        verify(user).setPaymentsCount(9 + 1);
        verify(response).sendRedirect(MAIN_SERVLET_PATH + USER_RECEIPTS_PATH);
        mockedAcc.close();
        mockedCard.close();
        mockedRec.close();
    }
}
