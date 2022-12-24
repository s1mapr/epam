package com.my.controllersTest;

import com.my.controllers.adminController.RequestsServlet;
import com.my.dto.RequestDTO;
import com.my.service.RequestService;
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

public class RequestsTest {

    @Test
    public void doGetTest() throws ServletException, IOException {
        final RequestsServlet servlet = new RequestsServlet();
        MockedStatic<RequestService> mockedStatic;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final List<RequestDTO> requests = new ArrayList<>();
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        mockedStatic = mockStatic(RequestService.class);
        when(request.getSession()).thenReturn(session);
        mockedStatic.when(RequestService::getListLength).thenReturn(21);
        when(request.getParameter("action")).thenReturn("block");
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("page")).thenReturn("1");
        when( session.getAttribute("reqPage")).thenReturn(1);
        mockedStatic.when(()->RequestService.getPageNumber("1",1)).thenReturn(1);
        mockedStatic.when(()->RequestService.getRequests(1)).thenReturn(requests);
        when(request.getRequestDispatcher("/views/jsp/requests.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute("pagesCount", 3);
        verify(session).setAttribute("reqPage", 1);
        verify(request).setAttribute("requests", requests);
        verify(dispatcher).forward(request,response);
        mockedStatic.close();
    }

}
