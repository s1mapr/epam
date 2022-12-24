package com.my.controllersTest;

import com.my.controllers.userController.EditProfileServlet;
import com.my.dto.UserDTO;
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

import static com.my.utils.HttpConstants.MAIN_SERVLET_PATH;
import static com.my.utils.HttpConstants.USER_PROFILE_PATH;
import static org.mockito.Mockito.*;

public class EditProfileTest {
    @Test
    public void doGetTest() throws ServletException, IOException {
        final Validation validation = new Validation();
        final EditProfileServlet servlet = new EditProfileServlet();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("imageError")).thenReturn(null);
        when(session.getAttribute("valid")).thenReturn(validation);
        when(request.getRequestDispatcher("/views/jsp/editProfile.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(session, times(1)).removeAttribute("valid");
        verify(request, times(1)).setAttribute("valid", validation);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPostTest() throws IOException, ServletException {

        final EditProfileServlet servlet = new EditProfileServlet();
        final UserDTO user = new UserDTO();
        final UserDTO newUser = new UserDTO();
        MockedStatic<UserService> mockedSettings;
        final Validation validation = mock(Validation.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        mockedSettings = mockStatic(UserService.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        user.setId(2);
        when(request.getParameter("firstName")).thenReturn("Maksym");
        when(request.getParameter("lastName")).thenReturn("Prokopenko");
        when(request.getParameter("email")).thenReturn("s1maprokopenko@gmail.com");
        when(request.getParameter("phoneNumber")).thenReturn("+380976284216");
        when(validation.updateProfileValidation(request.getParameter("firstName"), request.getParameter("lastName"),
                request.getParameter("email"), request.getParameter("phoneNumber")))
                .thenReturn(false);
        mockedSettings.when(()->UserService.updateUserData(user, "Maksym", "Prokopenko", "s1maprokopenko@gmail.com", "+380976284216", user.getId())).thenReturn(newUser);
        servlet.doPost(request, response);
        mockedSettings.verify(()->UserService.updateUserData(user, "Maksym", "Prokopenko", "s1maprokopenko@gmail.com", "+380976284216", user.getId()), times(1));
        verify(session, times(1)).setAttribute("user", newUser);
        verify(response).sendRedirect(MAIN_SERVLET_PATH + USER_PROFILE_PATH);
        mockedSettings.close();
    }


}
