package com.my.controllersTest;
import com.my.controllers.LangServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.my.utils.HttpConstants.*;
import static org.mockito.Mockito.*;
public class LangServletTest {
    @Test
    public void LangTest() throws IOException, ServletException {
        final LangServlet servlet = new LangServlet();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("lang")).thenReturn("lang");
        when(request.getHeader("Referer")).thenReturn("Referer");
        servlet.doGet(request, response);
        verify(request, times(1)).getParameter("lang");
        verify(request, times(1)).getHeader("Referer");
        verify(session, times(1)).setAttribute("lang", "lang");
        verify(response).sendRedirect("Referer");
    }
}
