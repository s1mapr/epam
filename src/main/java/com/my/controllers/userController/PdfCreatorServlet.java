package com.my.controllers.userController;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfEncodings;
import com.itextpdf.text.pdf.PdfWriter;
import com.my.dao.ReceiptDAO;
import com.my.dto.ReceiptDTO;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.my.utils.HttpConstants.*;

@WebServlet(USER_CREATE_PDF)
public class PdfCreatorServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(PdfCreatorServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Document document = new Document();
        String imgPath = this.getServletContext().getRealPath("") + "/views/img/paidUkr.png";
        String fontPath = this.getServletContext().getRealPath("") + "/views/font/FreeSans.ttf";
        HttpSession session = req.getSession();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
            log.info("create instance of PdfWriter");
        } catch (DocumentException e) {
            log.error("problem with creating instance of PdfWriter");
            log.error("Exception -  " + e);
        }
        Font font = FontFactory.getFont(fontPath, "Cp1251", true);

        document.setMargins(2, 2, 2, 2);
        Image image = null;
        try {
            image = Image.getInstance(imgPath);
            image.setAlignment(2);
            log.info("create instance of Image");
        } catch (BadElementException e) {
            log.error("problem with creating instance of Image");
            log.error("Exception -  " + e);
        }
        String purpose = req.getParameter("purpose");
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss");
        Paragraph purposeParagraph = new Paragraph(req.getParameter("purpose"), font);
        purposeParagraph.setAlignment(1);
        Paragraph detailsParagraph = new Paragraph("Детальніше", font);
        detailsParagraph.setAlignment(1);
        Paragraph mainParagraph = new Paragraph("Квитанція від " + LocalDateTime.now().format(formatter), font);
        mainParagraph.setAlignment(1);
        switch (purpose) {
            case "Поповнення телефону":
                document.setPageSize(new Rectangle(500, 380));
                document.open();
                try {
                    ReceiptDTO receipt = ReceiptDAO.getReceiptInfoPhone(Integer.parseInt(req.getParameter("id")), session);
                    document.add(mainParagraph);
                    document.add(new Paragraph("Платіж: " + receipt.getPaymentName(), font));
                    document.add(new Paragraph("Дата платежу: " + receipt.getPaymentDate(), font));
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
                    document.add(purposeParagraph);
                    document.add(new Paragraph("Номер карти: " + receipt.getUserCard(), font));
                    document.add(new Paragraph("Ім'я: " + receipt.getUserFirstName(), font));
                    document.add(new Paragraph("Прізвище: " + receipt.getUserLastName(), font));
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
                    document.add(detailsParagraph);
                    document.add(new Paragraph("Номер телефону: " + receipt.getPhoneNumber(), font));
                    document.add(new Paragraph("Сума поповнення: " + receipt.getAmount(), font));
                    document.add(new Paragraph("Статус платежу: " + receipt.getPaymentStatus(), font));
                    document.add(image);
                    log.info("generate pdf report for phone recharge");
                } catch (DocumentException e) {
                    log.error("problem with generating pdf report for phone recharge");
                    log.error("Exception -  " + e);
                }
                document.close();
                break;
            case "Оплата послуг":
                document.setPageSize(new Rectangle(500, 400));
                document.open();
                try {
                    ReceiptDTO receipt = ReceiptDAO.getReceiptInfoService(Integer.parseInt(req.getParameter("id")), session);
                    document.add(mainParagraph);
                    document.add(new Paragraph("Платіж: " + receipt.getPaymentName(), font));
                    document.add(new Paragraph("Дата платежу: " + receipt.getPaymentDate(), font));
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
                    document.add(purposeParagraph);
                    document.add(new Paragraph("Номер карти: " + receipt.getUserCard(), font));
                    document.add(new Paragraph("Ім'я: " + receipt.getUserFirstName(), font));
                    document.add(new Paragraph("Прізвище: " + receipt.getUserLastName(), font));
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
                    document.add(detailsParagraph);
                    document.add(new Paragraph("Номер картки: " + receipt.getPaymentCardNumber(), font));
                    document.add(new Paragraph("Послуга: " + receipt.getPurpose(), font));
                    document.add(new Paragraph("Сума платежу: " + receipt.getAmount(), font));
                    document.add(new Paragraph("Статус платежу: " + receipt.getPaymentStatus(), font));
                    document.add(image);
                    log.info("generate pdf report for service payment");
                } catch (DocumentException e) {
                    log.error("problem with generating pdf report for service payment");
                    log.error("Exception -  " + e);
                }
                document.close();
                break;
            case "Переказ на картку":
                document.setPageSize(new Rectangle(500, 410));
                document.open();
                try {
                    ReceiptDTO receipt = ReceiptDAO.getReceiptInfoCard(Integer.parseInt(req.getParameter("id")), session);
                    document.add(mainParagraph);
                    document.add(new Paragraph("Платіж: " + receipt.getPaymentName(), font));
                    document.add(new Paragraph("Дата платежу: " + receipt.getPaymentDate(), font));
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
                    document.add(purposeParagraph);
                    document.add(new Paragraph("Номер карти: " + receipt.getUserCard(), font));
                    document.add(new Paragraph("Ім'я: " + receipt.getUserFirstName(), font));
                    document.add(new Paragraph("Прізвище: " + receipt.getUserLastName(), font));
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
                    document.add(detailsParagraph);
                    document.add(new Paragraph("Номер картки: " + receipt.getPaymentCardNumber(), font));
                    document.add(new Paragraph("Ім'я одержувача: " + receipt.getPaymentFirstName(), font));
                    document.add(new Paragraph("Прізвище одержувача: " + receipt.getPaymentLastName(), font));
                    document.add(new Paragraph("Сума переказу: " + receipt.getAmount(), font));
                    document.add(new Paragraph("Статус переказу: " + receipt.getPaymentStatus(), font));
                    document.add(image);
                    log.info("generate pdf report for card transfer");
                } catch (DocumentException e) {
                    log.error("problem with generating pdf report for card transfer");
                    log.error("Exception -  " + e);
                }
                document.close();
                break;
            case "Оплата комунальних послуг":
                document.setPageSize(new Rectangle(500, 450));
                document.open();
                try {
                    ReceiptDTO receipt = ReceiptDAO.getReceiptInfoUtilities(Integer.parseInt(req.getParameter("id")), session);
                    document.add(mainParagraph);
                    document.add(new Paragraph("Платіж: " + receipt.getPaymentName(), font));
                    document.add(new Paragraph("Дата платежу: " + receipt.getPaymentDate(), font));
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
                    document.add(purposeParagraph);
                    document.add(new Paragraph("Номер карти: " + receipt.getUserCard(), font));
                    document.add(new Paragraph("Ім'я: " + receipt.getUserFirstName(), font));
                    document.add(new Paragraph("Прізвище: " + receipt.getUserLastName(), font));
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
                    document.add(detailsParagraph);
                    document.add(new Paragraph("Показання води: " + receipt.getMeterW(), font));
                    document.add(new Paragraph("Сума: " + receipt.getAmountW(), font));
                    document.add(new Paragraph("Показання електрики: " + receipt.getMeterE(), font));
                    document.add(new Paragraph("Сума: " + receipt.getAmountE(), font));
                    document.add(new Paragraph("Показання газу: " + receipt.getMeterG(), font));
                    document.add(new Paragraph("Сума: " + receipt.getAmountG(), font));
                    document.add(new Paragraph("Загальна сума: " + receipt.getAmount(), font));
                    document.add(new Paragraph("Статус оплати: " + receipt.getPaymentStatus(), font));
                    document.add(image);
                    log.info("generate pdf report for utilities payment");
                } catch (DocumentException e) {
                    log.error("problem with generating pdf report for utilities payment");
                    log.error("Exception -  " + e);
                }
                document.close();
                break;
            case "Оплата штрафів":
                document.setPageSize(new Rectangle(500, 440));
                document.open();
                try {
                    ReceiptDTO receipt = ReceiptDAO.getReceiptInfoFines(Integer.parseInt(req.getParameter("id")), session);
                    document.add(mainParagraph);
                    document.add(new Paragraph("Платіж: " + receipt.getPaymentName(), font));
                    document.add(new Paragraph("Дата платежу: " + receipt.getPaymentDate(), font));
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
                    document.add(purposeParagraph);
                    document.add(new Paragraph("Номер карти: " + receipt.getUserCard(), font));
                    document.add(new Paragraph("Ім'я: " + receipt.getUserFirstName(), font));
                    document.add(new Paragraph("Прізвище: " + receipt.getUserLastName(), font));
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
                    document.add(detailsParagraph);
                    document.add(new Paragraph("Ім'я: " + receipt.getPaymentFirstName(), font));
                    document.add(new Paragraph("Прізвище: " + receipt.getPaymentLastName(), font));
                    document.add(new Paragraph("По-батькові: " + receipt.getPaymentPatronymic(), font));
                    document.add(new Paragraph("Номер штрафу: " + receipt.getFineNumber(), font));
                    document.add(new Paragraph("Сума: " + receipt.getAmount(), font));
                    document.add(new Paragraph("Статус оплати: " + receipt.getPaymentStatus(), font));
                    document.add(image);
                    log.info("generate pdf report for fines payment");
                } catch (DocumentException e) {
                    log.error("problem with generating pdf report for fines payment");
                    log.error("Exception -  " + e);
                }
                document.close();
                break;
        }


        File downloadFile = new File("iTextHelloWorld.pdf");
        FileInputStream inStream = new FileInputStream(downloadFile);

        // obtains ServletContext
        ServletContext context = getServletContext();

        // gets MIME type of the file
        String mimeType = context.getMimeType("iTextHelloWorld.pdf");
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
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
        resp.sendRedirect(MAIN_SERVLET_PATH + USER_RECEIPTS_PATH);

    }
}
