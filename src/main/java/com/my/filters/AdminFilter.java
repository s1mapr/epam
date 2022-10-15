package com.my.filters;

import com.my.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import static com.my.utils.HttpConstants.*;
@WebFilter("/adm/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(Objects.nonNull(user) && user.getRole().equals("admin")){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            response.sendRedirect(MAIN_SERVLET_PATH+AUTHORIZATION_PATH);
        }
    }
}
