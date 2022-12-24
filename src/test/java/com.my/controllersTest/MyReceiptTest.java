package com.my.controllersTest;

import com.my.controllers.userController.MyReceiptsServlet;
import com.my.dto.UserDTO;
import com.my.service.ReceiptService;
import com.my.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class MyReceiptTest {
    private static final String query="SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? LIMIT 10 OFFSET ?";
    private static final String newQuery="SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? LIMIT 10 OFFSET ?";

    @Test
    public void doGetTest() throws ServletException, IOException {
        final UserDTO user = new UserDTO();
        final MyReceiptsServlet servlet = new MyReceiptsServlet();
        MockedStatic<UserService> mockedSettings;
        MockedStatic<ReceiptService> mockedSettings1;
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        mockedSettings = mockStatic(UserService.class);
        mockedSettings1 = mockStatic((ReceiptService.class));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        user.setId(2);
        mockedSettings1.when(()->ReceiptService.getReceiptListLength(user.getId())).thenReturn(15);
        when(session.getAttribute("query")).thenReturn(query);
        when((request.getParameter("sortAction"))).thenReturn("smth");
        when(request.getParameter("type")).thenReturn("ASC");
        mockedSettings1.when(()->ReceiptService.getPageNumber("1", 1)).thenReturn(2);
        mockedSettings1.when(()->ReceiptService.getQuery("smth", "ASC", query)).thenReturn(newQuery);
        when(request.getRequestDispatcher("/views/jsp/myReceipts.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
        mockedSettings.close();
        mockedSettings1.close();
    }
}
