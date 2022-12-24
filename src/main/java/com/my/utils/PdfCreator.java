package com.my.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
/**
 * PdfCreator utils
 */
public class PdfCreator {
    private static final Logger log = Logger.getLogger(PdfCreator.class);

    /**
     * initialization of image
     */
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
    /**
     * creates pdf instance
     */
    public static void pdfGetInstance(Document document) throws FileNotFoundException {
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Receipt.pdf"));
            log.info("create instance of PdfWriter");
        } catch (DocumentException e) {
            log.error("problem with creating instance of PdfWriter");
            log.error("Exception -  " + e);
        }
    }

    /**
     * Creating pdf document
     */
    public static void createPDF(HttpServletResponse resp, ServletContext context) throws IOException {
        File downloadFile = new File("Receipt.pdf");
        FileInputStream inStream = new FileInputStream(downloadFile);
        String mimeType = context.getMimeType("Receipt.pdf");
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        resp.setContentType(mimeType);
        resp.setContentLength((int) downloadFile.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        resp.setHeader(headerKey, headerValue);

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
