package com.my.dtoTest;

import com.my.dto.AccountDTO;
import com.my.dto.ReceiptDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountDtoTest {
    @Test
    public void BuilderNameTest() {
        AccountDTO user = new AccountDTO.Builder()
                .name("name")
                .build();
        assertEquals("name", user.getName());
    }

    @Test
    public void BuilderIdTest() {
        AccountDTO user = new AccountDTO.Builder()
                .id(10)
                .build();
        assertEquals(10, user.getId());
    }

    @Test
    public void BuilderAmountTest() {
        AccountDTO user = new AccountDTO.Builder()
                .amount(11.1)
                .build();
        assertEquals(11.1, user.getAmount());
    }

    @Test
    public void BuilderStatusTest() {
        AccountDTO user = new AccountDTO.Builder()
                .status("status")
                .build();
        assertEquals("status", user.getStatus());
    }

    @Test
    public void BuilderUserLoginTest() {
        AccountDTO user = new AccountDTO.Builder()
                .userLogin("login")
                .build();
        assertEquals("login", user.getUserLogin());
    }

    @Test
    public void BuilderUserFirstNameTest() {
        AccountDTO user = new AccountDTO.Builder()
                .userFirstName("name")
                .build();
        assertEquals("name", user.getUserFirstName());
    }

    @Test
    public void BuilderUserLastNameTest() {
        AccountDTO user = new AccountDTO.Builder()
                .userLastName("lName")
                .build();
        assertEquals("lName", user.getUserLastName());
    }

    @Test
    public void BuilderPaymentCountTest() {
        AccountDTO user = new AccountDTO.Builder()
                .paymentsCount(33)
                .build();
        assertEquals(33, user.getPaymentsCount());
    }

    @Test
    public void BuilderCardNumberTest() {
        AccountDTO user = new AccountDTO.Builder()
                .cardNumber("number")
                .build();
        assertEquals("number", user.getCardNumber());
    }
}
