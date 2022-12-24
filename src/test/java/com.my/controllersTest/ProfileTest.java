package com.my.controllersTest;

import com.my.controllers.userController.ProfileServlet;
import com.my.dto.AccountDTO;
import com.my.dto.UserDTO;
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

public class ProfileTest {

    @Test
    public void doGetTest() throws ServletException, IOException {
        final ProfileServlet servlet = new ProfileServlet();
        final UserDTO user = new UserDTO();
        MockedStatic<AccountService> mockedAcc;
        final List<AccountDTO> list = new ArrayList<>();
        final List<AccountDTO> accounts = new ArrayList<>();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        mockedAcc = mockStatic(AccountService.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        user.setId(12);
        mockedAcc.when(()->AccountService.getUserAccountsListLength(user.getId())).thenReturn(22);
        when(request.getParameter("action")).thenReturn("unblock");
        when(request.getParameter("id")).thenReturn("12");
        when(session.getAttribute("profileQuery")).thenReturn("query");
        when(request.getParameter("page")).thenReturn("1");
        when(session.getAttribute("profPage")).thenReturn(1);
        mockedAcc.when(()->AccountService.getPageNumber("1", 1)).thenReturn(1);
        when(request.getParameter("sortAction")).thenReturn("sort");
        when(request.getParameter("type")).thenReturn("ASC");
        mockedAcc.when(()->AccountService.getQueryForUserAccounts("sort", "ASC", "query")).thenReturn("newQuery");
        mockedAcc.when(()->AccountService.getUserAccountsWithPagination(user.getId(), 1, "newQuery")).thenReturn(list);
        mockedAcc.when(()->AccountService.getUserAccountsWithoutPagination(user.getId())).thenReturn(accounts);
        when(request.getRequestDispatcher("/views/jsp/profile.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("pagesCount", 3);
        verify(session).setAttribute("accounts", accounts);
        verify(session).setAttribute("profPage", 1);
        verify(session).setAttribute("profileQuery", "newQuery");
        verify(request).setAttribute("accountsPag", list);
        verify(dispatcher).forward(request, response);
        mockedAcc.close();
    }
}
