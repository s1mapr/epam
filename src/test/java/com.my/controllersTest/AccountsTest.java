package com.my.controllersTest;

import com.my.controllers.adminController.AccountsServlet;
import com.my.dto.AccountDTO;
import com.my.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AccountsTest {
    @Test
    public void doGetTest() throws ServletException, IOException {
        final AccountsServlet servlet = new AccountsServlet();
        MockedStatic<AccountService> mockedStatic;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final List<AccountDTO> accounts = new ArrayList<>();
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        mockedStatic = mockStatic(AccountService.class);
        when(request.getSession()).thenReturn(session);
        mockedStatic.when(AccountService::getListLength).thenReturn(32);
        when(request.getParameter("action")).thenReturn("block");
        when(request.getParameter("id")).thenReturn("2");
        when(session.getAttribute("accountQuery")).thenReturn("query");
        when(request.getParameter("page")).thenReturn("1");
        when(session.getAttribute("accPage")).thenReturn(1);
        mockedStatic.when(()->AccountService.getPageNumber("1", 1)).thenReturn(1);
        when(request.getParameter("sortAction")).thenReturn("someSort");
        when(request.getParameter("type")).thenReturn("ASC");
        mockedStatic.when(()->AccountService.getQueryForAdminAccounts("someSort", "ASC", "query")).thenReturn("newQuery");
        mockedStatic.when(()->AccountService.getAllAccountsWithPagination(1, "newQuery")).thenReturn(accounts);
        when(request.getRequestDispatcher("/views/jsp/accounts.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("pagesCount", 4);
        verify(session).setAttribute("accPage",1);
        verify(session).setAttribute("accountQuery", "newQuery");
        verify(request).setAttribute("list", accounts);
        verify(dispatcher).forward(request, response);
        mockedStatic.close();
    }
}
