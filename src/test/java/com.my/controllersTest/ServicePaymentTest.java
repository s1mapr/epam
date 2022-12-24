package com.my.controllersTest;

import com.my.controllers.userController.servicesPayments.ServicesPaymentServlet;
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

public class ServicePaymentTest {

    @Test
    public void doGet() throws ServletException, IOException {
        final ServicesPaymentServlet servlet = new ServicesPaymentServlet();
        final Validation validation = mock(Validation.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("valid")).thenReturn(validation);
        when(session.getAttribute("notEnoughMoney")).thenReturn(null);
        when(request.getRequestDispatcher("/views/jsp/options/servicesPayment.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(session).removeAttribute("valid");
        verify(request).setAttribute("valid", validation);
        verify(session).setAttribute("purposeId", 2);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        final Validation validation = mock(Validation.class);
        final ServicesPaymentServlet servlet = new ServicesPaymentServlet();
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
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("card")).thenReturn("7896555522223562");
        when(request.getParameter("name")).thenReturn("Tourism");
        when(request.getParameter("amount")).thenReturn("22");
        when(validation.servicesPaymentValidation("7896555522223562","Tourism", "22" )).thenReturn(true);
        when(session.getAttribute("purposeId")).thenReturn(2);
        when(request.getParameter("accountId")).thenReturn("30");
        mockedAcc.when(()->AccountService.getCardId(30)).thenReturn(31);
        mockedCard.when(()->CardService.getAmount(31)).thenReturn(777.0);
        mockedRec.when(()->ReceiptService.createNewEntryInServService("7896555522223562", "Tourism")).thenReturn(20);
        user.setPaymentsCount(10);
        servlet.doPost(request, response);
        verify(user).setPaymentsCount(9 + 1);
        verify(response).sendRedirect(MAIN_SERVLET_PATH + USER_RECEIPTS_PATH);
        mockedAcc.close();
        mockedCard.close();
        mockedRec.close();
    }

}
