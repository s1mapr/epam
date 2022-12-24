package com.my.controllers.userController;

import com.itextpdf.text.*;
import com.my.dto.UserDTO;
import com.my.service.ReceiptService;
import com.my.utils.PdfCreator;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

import static com.my.utils.HttpConstants.*;
/**
 * Controller for pdf creator
 */
@WebServlet(USER_CREATE_PDF)
public class PdfCreatorServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(PdfCreatorServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        String imgPath = this.getServletContext().getRealPath("") + "/views/img/paidUkr.png";
        String fontPath = this.getServletContext().getRealPath("") + "/views/font/FreeSans.ttf";
        ServletContext context = getServletContext();
        Document document = new Document();
        document.setMargins(2, 2, 2, 2);
        PdfCreator.pdfGetInstance(document);
        String purpose = req.getParameter("purpose");
        int id = Integer.parseInt(req.getParameter("id"));
        ReceiptService.setPdfData(purpose, document, user, id, imgPath, fontPath);
        PdfCreator.createPDF(resp, context);
        resp.sendRedirect(MAIN_SERVLET_PATH + USER_RECEIPTS_PATH);
    }
}
