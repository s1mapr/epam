package com.my.dtoTest;

import com.my.dto.ReceiptDTO;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceiptDtoTest {

    @Test
    public void BuilderPaymentNameTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .paymentName("payment")
                .build();
        assertEquals("payment", user.getPaymentName());
    }

    @Test
    public void BuilderPaymentDateTest() {
        Date date = Date.valueOf(LocalDate.now());
        ReceiptDTO user = new ReceiptDTO.Builder()
                .paymentDate(date)
                .build();
        assertEquals(date, user.getPaymentDate());
    }

    @Test
    public void BuilderAccountNameTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .accountName("accName")
                .build();
        assertEquals("accName", user.getAccountName());
    }

    @Test
    public void BuilderIdTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .id(12)
                .build();
        assertEquals(12, user.getId());
    }

    @Test
    public void BuilderUserCardTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .userCard("userCard")
                .build();
        assertEquals("userCard", user.getUserCard());
    }


    @Test
    public void BuilderUserFirstNameTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .userFirstName("userFirstName")
                .build();
        assertEquals("userFirstName", user.getUserFirstName());
    }


    @Test
    public void BuilderUserLastNameTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .userLastName("userLastName")
                .build();
        assertEquals("userLastName", user.getUserLastName());
    }


    @Test
    public void BuilderPhoneNumberTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .phoneNumber("phoneNumber")
                .build();
        assertEquals("phoneNumber", user.getPhoneNumber());
    }


    @Test
    public void BuilderAmountTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .amount(4.4)
                .build();
        assertEquals(4.4, user.getAmount());
    }


    @Test
    public void BuilderPaymentCardNumberTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .paymentCardNumber("number")
                .build();
        assertEquals("number", user.getPaymentCardNumber());
    }


    @Test
    public void BuilderPurposeTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .purpose("purpose")
                .build();
        assertEquals("purpose", user.getPurpose());
    }


    @Test
    public void BuilderPaymentStatusTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .paymentStatus("status")
                .build();
        assertEquals("status", user.getPaymentStatus());
    }


    @Test
    public void BuilderPaymentFirstNameTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .paymentFirstName("boba")
                .build();
        assertEquals("boba", user.getPaymentFirstName());
    }


    @Test
    public void BuilderPaymentLastNameTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .paymentLastName("last")
                .build();
        assertEquals("last", user.getPaymentLastName());
    }


    @Test
    public void BuilderPaymentPatronymicTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .paymentPatronymic("ptr")
                .build();
        assertEquals("ptr", user.getPaymentPatronymic());
    }


    @Test
    public void BuilderFineNumberTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .fineNumber("fine")
                .build();
        assertEquals("fine", user.getFineNumber());
    }


    @Test
    public void BuilderMeterWTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .meterW("meter")
                .build();
        assertEquals("meter", user.getMeterW());
    }


    @Test
    public void BuilderMeterETest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .meterE("meter")
                .build();
        assertEquals("meter", user.getMeterE());
    }

    @Test
    public void BuilderMeterGTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .meterG("meter")
                .build();
        assertEquals("meter", user.getMeterG());
    }


    @Test
    public void BuilderAmountWTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .amountW(11)
                .build();
        assertEquals(11, user.getAmountW());
    }


    @Test
    public void BuilderAmountETest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .amountE(11)
                .build();
        assertEquals(11, user.getAmountE());
    }


    @Test
    public void BuilderAmountGTest() {
        ReceiptDTO user = new ReceiptDTO.Builder()
                .amountG(11)
                .build();
        assertEquals(11, user.getAmountG());
    }

}
