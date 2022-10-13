package com.my.controllers;

import com.my.dao.UserDAO;
import com.my.entities.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@WebServlet("/upload")
public class uploadImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        File file = null;
        int maxFileSize = 5000 * 1024;
        int maxMemSize = 5000 * 1024;
        ServletContext context = req.getServletContext();
        String filePath = context.getInitParameter("file-upload");
        User user = (User) session.getAttribute("user");
        // Verify the content type
        String contentType = req.getContentType();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List<FileItem> fileItems = upload.parseRequest(req);

            File dir = new File(filePath + "\\" + user.getId() + "\\");
            if (!dir.exists()) {
                dir.mkdir();
            }

            // Process the uploaded file items
            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField()) {
                    // Get the uploaded file parameters

                    // Write the file
                    file = new File(filePath + "\\" + user.getId() + "\\avatar" + new File(filePath + "\\" + user.getId() + "\\").listFiles().length+ ".png");
                    fileItem.write(file);
                }
            }
            if (Objects.nonNull(file)) {
                UserDAO.setNewAvatar(file.getAbsolutePath(), user.getId());
                String avatarURL = file.getAbsolutePath().split("target")[1];
                user.setAvatarURL(avatarURL);
                session.removeAttribute("user");
                session.setAttribute("user", user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/epamProject/user/editProfile");
    }
}
