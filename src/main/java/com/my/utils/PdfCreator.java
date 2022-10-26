package com.my.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.my.dao.ReceiptDAO;
import com.my.dto.ReceiptDTO;
import com.my.dto.UserDTO;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfCreator {
    private static final Logger log = Logger.getLogger(PdfCreator.class);

    public static Image imageInitialization(String imgPath) {
        Image image = null;
        try {
            image = Image.getInstance(imgPath);
            image.setAlignment(2);
            log.info("create instance of Image");
        } catch (BadElementException | IOException e) {
            log.error("problem with creating instance of Image");
            log.error("Exception -  " + e);
        }
        return image;
    }

    public static void pdfGetInstance(Document document) throws FileNotFoundException {
        try {
            PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
            log.info("create instance of PdfWriter");
        } catch (DocumentException e) {
            log.error("problem with creating instance of PdfWriter");
            log.error("Exception -  " + e);
        }
    }
    public static void createPDF(HttpServletResponse resp, ServletContext context) throws IOException {
        File downloadFile = new File("iTextHelloWorld.pdf");
        FileInputStream inStream = new FileInputStream(downloadFile);

        // obtains ServletContext

        // gets MIME type of the file
        String mimeType = context.getMimeType("iTextHelloWorld.pdf");
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        // modifies response
        resp.setContentType(mimeType);
        resp.setContentLength((int) downloadFile.length());

        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        resp.setHeader(headerKey, headerValue);

        // obtains response's output stream
        OutputStream outStream = resp.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        inStream.close();
        outStream.close();
    }
}
