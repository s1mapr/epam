package com.my.controllers.userController;

import com.my.dao.UserDAO;
import com.my.entities.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

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
import java.util.Objects;

import static com.my.utils.HttpConstants.*;

@WebServlet(USER_UPLOAD_PATH)
public class UploadImageServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UploadImageServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        File file = null;
        int maxFileSize = 5000 * 1024;
        int maxMemSize = 5000 * 1024;
        ServletContext context = req.getServletContext();
        String filePath = context.getInitParameter("file-upload");
        User user = (User) session.getAttribute("user");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize);
        factory.setRepository(new File("c:\\temp"));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(maxFileSize);

        try {
            List<FileItem> fileItems = upload.parseRequest(req);

            File dir = new File(filePath + "\\" + user.getId() + "\\");
            if (!dir.exists()) {
                dir.mkdir();
            }

            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField()) {
                    String fileName = new File(fileItem.getName()).getName();
                    if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
                        session.setAttribute("imageError","msg");
                        resp.sendRedirect(MAIN_SERVLET_PATH + USER_EDIT_PROFILE_PATH);
                        return;
                    }
                    file = new File(filePath + "\\" + user.getId() + "\\avatar" + new File(filePath + "\\" + user.getId() + "\\").listFiles().length + ".png");
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
            log.info("upload new avatar");
        } catch (Exception e) {
            log.error("problem with uploading new avatar");
            log.error("Exception -  " + e);
        }
        resp.sendRedirect(MAIN_SERVLET_PATH + USER_EDIT_PROFILE_PATH);
    }
}
