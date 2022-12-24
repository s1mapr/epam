package com.my.controllers.userController;

import com.my.dto.UserDTO;
import com.my.service.UserService;
import com.my.utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

import static com.my.utils.HttpConstants.*;
/**
 * Controller for editing profile
 */
@WebServlet(USER_EDIT_PROFILE_PATH)
public class EditProfileServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(Objects.nonNull(session.getAttribute("imageError"))){
            session.removeAttribute("imageError");
            req.setAttribute("imageError", "msg");
        }
        Validation validation = (Validation) session.getAttribute("valid");
        session.removeAttribute("valid");
        req.setAttribute("valid", validation);
        req.getRequestDispatcher("/views/jsp/editProfile.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        int userId = user.getId();
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        Validation validation = new Validation();
        boolean isValid = validation.updateProfileValidation(firstName, lastName, email, phoneNumber);
        if (!isValid) {
            session.setAttribute("valid", validation);
            resp.sendRedirect(MAIN_SERVLET_PATH + USER_EDIT_PROFILE_PATH);
            return;
        }
        UserDTO newUser = UserService.updateUserData(user, firstName, lastName, email, phoneNumber, userId);
        session.setAttribute("user", newUser);
        resp.sendRedirect(MAIN_SERVLET_PATH + USER_PROFILE_PATH);
    }
}
