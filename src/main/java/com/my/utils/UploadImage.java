package com.my.utils;

import com.my.controllers.userController.UploadImageServlet;
import com.my.dto.UserDTO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Objects;
/**
 * UploadImage utils
 */
public class UploadImage {
    private static final Logger log = Logger.getLogger(UploadImage.class);

    /**
     * Return file object
     */
    public static File getFile(UserDTO user, String filePath, FileItem fileItem) {
        File file;
        file = new File(filePath + "\\" + user.getId() + "\\avatar" + Objects.requireNonNull(new File(filePath + "\\" + user.getId() + "\\").listFiles()).length + ".png");
        try {
            fileItem.write(file);
            log.info("write file");
        } catch (Exception e) {
            log.error("problem with writing file");
            log.error("Exception -  " + e);
        }
        return file;
    }

    /**
     * Return file name
     */

    public static String getFileName(FileItem fileItem) {
        String fileName = null;
        if (!fileItem.isFormField()) {
            fileName = new File(fileItem.getName()).getName();
        }
        return fileName;
    }

    /**
     * Return list of file items
     */

    public static List<FileItem> getFileItems(HttpServletRequest req, UserDTO user, String filePath) {
        ServletFileUpload upload = getUpload();
        List<FileItem> fileItems = null;
        try {
            fileItems = upload.parseRequest(req);
            File dir = new File(filePath + "\\" + user.getId() + "\\");
            if (!dir.exists()) {
                dir.mkdir();
            }
            log.info("upload new avatar");
        } catch (Exception e) {
            log.error("problem with uploading new avatar");
            log.error("Exception -  " + e);
        }
        return fileItems;
    }

    /**
     * Return ServletFileUpload object
     */

    private static ServletFileUpload getUpload(){
        int maxFileSize = 5000 * 1024;
        int maxMemSize = 5000 * 1024;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize);
        factory.setRepository(new File("c:\\temp"));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(maxFileSize);
        return upload;
    }
}
