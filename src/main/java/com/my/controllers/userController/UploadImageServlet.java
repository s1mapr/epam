package com.my.controllers.userController;

import com.my.dto.UserDTO;
import com.my.service.UserService;
import com.my.utils.UploadImage;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.my.utils.HttpConstants.*;
/**
 * Controller for uploading image
 */
@WebServlet(USER_UPLOAD_PATH)
public class UploadImageServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        ServletContext context = req.getServletContext();
        String filePath = context.getInitParameter("file-upload");
        List<FileItem> fileItems = UploadImage.getFileItems(req, user, filePath);
        FileItem fileItem = fileItems.get(0);
        String fileName = UploadImage.getFileName(fileItem);
        if (!fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".png")) {
            session.setAttribute("imageError", "msg");
            resp.sendRedirect(MAIN_SERVLET_PATH + USER_EDIT_PROFILE_PATH);
            return;
        }
        File file = UploadImage.getFile(user, filePath, fileItem);
        UserService.setNewAvatar(file.getAbsolutePath(), user.getId());
        String avatarURL = file.getAbsolutePath().split("target")[1];
        user.setAvatarURL(avatarURL);
        session.setAttribute("user", user);
        resp.sendRedirect(MAIN_SERVLET_PATH + USER_EDIT_PROFILE_PATH);
    }

}
