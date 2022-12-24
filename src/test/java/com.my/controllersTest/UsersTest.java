package com.my.controllersTest;

import com.my.controllers.adminController.UsersServlet;
import com.my.dto.UserDTO;
import com.my.service.UserService;
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
import static org.mockito.Mockito.verify;

public class UsersTest {
    @Test
    public void doGetTest() throws ServletException, IOException {
        final UsersServlet servlet = new UsersServlet();
        MockedStatic<UserService> mockedStatic;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final List<UserDTO> users = new ArrayList<>();
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        mockedStatic = mockStatic(UserService.class);
        when(request.getSession()).thenReturn(session);
        mockedStatic.when(UserService::getListLength).thenReturn(32);
        when(request.getParameter("action")).thenReturn("block");
        when(request.getParameter("id")).thenReturn("2");
        when(session.getAttribute("usersQuery")).thenReturn("query");
        when(request.getParameter("page")).thenReturn("1");
        when(session.getAttribute("userPage")).thenReturn(1);
        mockedStatic.when(()->UserService.getPageNumber("1", 1)).thenReturn(1);
        when(request.getParameter("sortAction")).thenReturn("someSort");
        when(request.getParameter("type")).thenReturn("ASC");
        mockedStatic.when(()->UserService.getQuery("someSort", "ASC", "query")).thenReturn("newQuery");
        mockedStatic.when(()->UserService.getAllUsersWithPagination(1, "newQuery")).thenReturn(users);
        when(request.getRequestDispatcher("/views/jsp/users.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("pagesCount", 4);
        verify(session).setAttribute("userPage",1);
        verify(session).setAttribute("usersQuery", "newQuery");
        verify(request).setAttribute("list", users);
        verify(dispatcher).forward(request, response);
        mockedStatic.close();
    }
}
