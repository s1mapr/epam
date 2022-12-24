package com.my.controllersTest;

import com.my.service.AccountService;
import com.my.service.CardService;
import com.my.service.ReceiptService;
import com.my.controllers.userController.servicesPayments.CardTransferServlet;
import com.my.dto.UserDTO;
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

public class CardTransferTest {
    @Test
    public void doGetTest() throws ServletException, IOException {
        final CardTransferServlet servlet = new CardTransferServlet();
        final Validation validation = mock(Validation.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("valid")).thenReturn(validation);
        when(session.getAttribute("notEnoughMoney")).thenReturn(null);
        when(request.getRequestDispatcher("/views/jsp/options/cardTransfer.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(session).removeAttribute("valid");
        verify(request).setAttribute("valid", validation);
        verify(session).setAttribute("purposeId", 3);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        final Validation validation = mock(Validation.class);
        final CardTransferServlet servlet = new CardTransferServlet();
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
        when(request.getParameter("card")).thenReturn("7896589625441233");
        when(request.getParameter("firstName")).thenReturn("Maksym");
        when(request.getParameter("lastName")).thenReturn("Prokopenko");
        when(request.getParameter("amount")).thenReturn("123");
        when(validation.cardTransferValidation("7896589625441233", "Maksym", "Prokopenko", "123")).thenReturn(true);
        when(session.getAttribute("purposeId")).thenReturn(2);
        when(request.getParameter("accountId")).thenReturn("10");
        mockedAcc.when(() -> AccountService.getCardId(10)).thenReturn(9);
        mockedCard.when(() -> CardService.getAmount(9)).thenReturn(1555.0);
        mockedRec.when(() -> ReceiptService.createNewEntryInTransService("7896589625441233", "Maksym", "Prokopenko")).thenReturn(2);
        user.setPaymentsCount(4);
        servlet.doPost(request, response);
        verify(user).setPaymentsCount(3 + 1);
        verify(response).sendRedirect(MAIN_SERVLET_PATH + USER_RECEIPTS_PATH);
        mockedAcc.close();
        mockedCard.close();
        mockedRec.close();
    }

}
