package com.my.controllersTest;

import com.my.controllers.RegistrationServlet;
import com.my.service.UserService;
import com.my.utils.Validation;
import org.mockito.MockedStatic;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.my.utils.HttpConstants.*;
import static org.mockito.Mockito.*;

public class RegistrationTest {

    @Test
    public void doGetTest() throws ServletException, IOException {
        final Validation validation = mock(Validation.class);
        final RegistrationServlet servlet = new RegistrationServlet();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("regError")).thenReturn(null);
        when(session.getAttribute("valid")).thenReturn(validation);
        when(request.getRequestDispatcher("/views/jsp/registration.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(session, times(1)).removeAttribute("valid");
        verify(request, times(1)).setAttribute("valid", validation);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        final Validation validation = mock(Validation.class);
        final RegistrationServlet servlet = new RegistrationServlet();
        MockedStatic<UserService> mockedSettings;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        mockedSettings = mockStatic(UserService.class);
        when(request.getParameter("login")).thenReturn("User123");
        when(request.getParameter("password")).thenReturn("UserPass1");
        when(request.getParameter("firstName")).thenReturn("Maksym");
        when(request.getParameter("lastName")).thenReturn("Prokopenko");
        when(request.getParameter("email")).thenReturn("drmax1341@gmail.com");
        when(request.getParameter("phoneNumber")).thenReturn("+380976284216");
        when(request.getSession()).thenReturn(session);
        mockedSettings.when(()->UserService.getUser("User123")).thenReturn(true);
        when(validation.registrationValidation("User123", "UserPass1", "Maksym",
                "Prokopenko", "drmax1341@gmail.com", "+380976284216")).thenReturn(true);
        servlet.doPost(request, response);
        verify(response).sendRedirect(MAIN_SERVLET_PATH + AUTHORIZATION_PATH);
        mockedSettings.close();
    }
}
