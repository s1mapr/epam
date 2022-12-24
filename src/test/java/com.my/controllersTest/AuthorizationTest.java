package com.my.controllersTest;

import com.my.controllers.AuthorizationServlet;
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

import static com.my.utils.HttpConstants.AUTHORIZATION_PATH;
import static com.my.utils.HttpConstants.MAIN_SERVLET_PATH;
import static org.mockito.Mockito.*;

public class AuthorizationTest {
    @Test
    public void doGetTest() throws ServletException, IOException {
        final AuthorizationServlet servlet = new AuthorizationServlet();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("youAreBlocked")).thenReturn(null);
        when(session.getAttribute("loginError")).thenReturn(null);
        when(request.getRequestDispatcher("/views/jsp/authorization.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        final AuthorizationServlet servlet = new AuthorizationServlet();
        final UserDTO user = new UserDTO();
        MockedStatic<UserService> mockedSettings;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        mockedSettings = mockStatic(UserService.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn("SomeLogin");
        when(request.getParameter("password")).thenReturn("SomePassword1");
        mockedSettings.when(()->UserService.getUser("SomeLogin", "SomePassword1")).thenReturn(user);
        user.setStatus("blocked");
        servlet.doPost(request, response);
        verify(session).setAttribute("youAreBlocked", "msg");
        verify(response).sendRedirect(MAIN_SERVLET_PATH + AUTHORIZATION_PATH);
        mockedSettings.close();
    }
}
