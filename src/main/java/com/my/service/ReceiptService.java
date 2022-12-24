package com.my.service;

import com.itextpdf.text.*;
import com.my.dao.ReceiptDAO;
import com.my.dto.ReceiptDTO;
import com.my.dto.UserDTO;
import com.my.utils.PdfCreator;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
/**
 * Card service
 */
public class ReceiptService {
    private static final Logger log = Logger.getLogger(ReceiptService.class);
    public static String GET_RECEIPTS = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? LIMIT 10 OFFSET ?";
    public static String GET_RECEIPTS_SORTED_BY_AMOUNT = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY amount ";
    public static String GET_RECEIPTS_SORTED_BY_NAME = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY receipt.name ";
    public static String GET_RECEIPTS_SORTED_BY_DATE = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY date ";
    public static String GET_RECEIPTS_SORTED_BY_STATUS = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY receipt.status ";
    public static String GET_RECEIPTS_SORTED_BY_PURPOSE = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY purpose.name ";
    public static String GET_RECEIPTS_SORTED_BY_ACCOUNT = "SELECT * FROM receipt JOIN purpose ON purpose.id = receipt.purpose_id JOIN account ON account.id = receipt.account_id WHERE receipt.user_id = ? ORDER BY account.name ";

    /**
     * Sets header data of generated pdf document
     */

    private static void setHeaderData(Document document, ReceiptDTO receipt, String purpose, Font font) {
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss");
        Paragraph purposeParagraph = new Paragraph(purpose, font);
        purposeParagraph.setAlignment(1);
        Paragraph detailsParagraph = new Paragraph("Детальніше", font);
        detailsParagraph.setAlignment(1);
        Paragraph mainParagraph = new Paragraph("Квитанція від " + LocalDateTime.now().format(formatter), font);
        mainParagraph.setAlignment(1);
        try {
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
            log.info("generate pdf report header");
        } catch (DocumentException e) {
            log.error("problem with generating pdf report header");
            log.error("Exception -  " + e);
        }
    }

    /**
     * Sets data for generated pdf document
     */

    public static void setPdfData(String purpose, Document document, UserDTO user, int id, String imgPath, String fontPath){
        Image image = PdfCreator.imageInitialization(imgPath);
        Font font = FontFactory.getFont(fontPath, "Cp1251", true);

        switch (purpose) {
            case "Поповнення телефону":
                document.setPageSize(new Rectangle(500, 380));
                document.open();
                try {
                    ReceiptDTO receipt = ReceiptDAO.getReceiptInfoPhone(id, user);
                    setHeaderData(document, receipt, purpose, font);
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
                    ReceiptDTO receipt = ReceiptDAO.getReceiptInfoService(id, user);
                    setHeaderData(document, receipt, purpose, font);
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
                    ReceiptDTO receipt = ReceiptDAO.getReceiptInfoCard(id, user);
                    setHeaderData(document, receipt, purpose, font);
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
                    ReceiptDTO receipt = ReceiptDAO.getReceiptInfoUtilities(id, user);
                    setHeaderData(document, receipt, purpose, font);
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
                    ReceiptDTO receipt = ReceiptDAO.getReceiptInfoFines(id, user);
                    setHeaderData(document, receipt, purpose, font);
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
    }

    /**
     * Updates receipt status
     */

    public static void updateStatus(){
        ReceiptDAO.updateStatus();
    }

    /**
     * Return count of user payments
     */

    public static int getReceiptListLength(int userId){
        return ReceiptDAO.getPaymentsCountOfUser(userId);
    }

    /**
     * Return query for pagination
     */

    public static String getQuery(String action, String type, String oldQuery) {
        if(Objects.nonNull(oldQuery) &&Objects.isNull(action)){
            return oldQuery;
        } else if (Objects.nonNull(action)) {
            switch (action) {
                case "sortAmount":
                    return GET_RECEIPTS_SORTED_BY_AMOUNT + type + " LIMIT 10 OFFSET ?";
                case "sortName":
                    return GET_RECEIPTS_SORTED_BY_NAME + type + " LIMIT 10 OFFSET ?";
                case "sortDate":
                    return GET_RECEIPTS_SORTED_BY_DATE + type + " LIMIT 10 OFFSET ?";
                case "sortStatus":
                    return GET_RECEIPTS_SORTED_BY_STATUS + type + " LIMIT 10 OFFSET ?";
                case "sortAccount":
                    return GET_RECEIPTS_SORTED_BY_ACCOUNT + type + " LIMIT 10 OFFSET ?";
                case "sortPurpose":
                    return GET_RECEIPTS_SORTED_BY_PURPOSE + type + " LIMIT 10 OFFSET ?";
            }
        }
        return GET_RECEIPTS;
    }

    /**
     * Return page number for pagination
     */

    public static int getPageNumber(String page, Integer pageNumberObj){
        int pageNumber;
        if (Objects.nonNull(page)){
            pageNumber = Integer.parseInt(page);
        } else if (Objects.isNull(pageNumberObj)) {
            pageNumber = 1;
        } else {
            pageNumber = pageNumberObj;
        }
        return pageNumber;
    }

    /**
     * Return user receipts for pagination
     */

    public static List<ReceiptDTO> getReceiptsWithPagination(int userId, int pageNumber, String newQuery){
        return ReceiptDAO.getUsersReceiptsWithPagination(userId, pageNumber, newQuery);
    }

    /**
     * Creates new entry in database witch has information about receipt
     */

    public static void createEntryInReceipt(int accountId, int purposeId, double amount, int serviceId, int userId){
        ReceiptDAO.createEntryInReceipt(accountId, purposeId, amount, serviceId, userId);
    }
    /**
     * Creates new entry in database witch has information about receipt
     */
    public static int createNewEntryInTransService(String cardNumber, String firstName, String lastName){
        return ReceiptDAO.createNewEntryInTransService(cardNumber, firstName, lastName);
    }
    /**
     * Creates new entry in database witch has information about receipt
     */
    public static int createNewEntryInFinesService(String firstName, String lastName, String patronymic, String fineNumber){
        return ReceiptDAO.createNewEntryInFinesService(firstName, lastName, patronymic, fineNumber);
    }
    /**
     * Creates new entry in database witch has information about receipt
     */
    public static int createNewEntryInPhoneService(String number){
        return ReceiptDAO.createNewEntryInPhoneService(number);
    }
    /**
     * Creates new entry in database witch has information about receipt
     */
    public static int createNewEntryInServService(String cardNumber, String serviceName){
        return ReceiptDAO.createNewEntryInServService(cardNumber, serviceName);
    }
    /**
     * Creates new entry in database witch has information about receipt
     */
    public static int createNewEntryInUtilitiesService(int meterW, int meterE, int meterG, double amountW, double amountE, double amountG){
        return ReceiptDAO.createNewEntryInUtilitiesService(meterW, meterE, meterG, amountW, amountE, amountG);
    }
}
