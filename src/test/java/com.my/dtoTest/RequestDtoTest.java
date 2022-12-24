package com.my.dtoTest;

import com.my.dto.RequestDTO;
import com.my.dto.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestDtoTest {
    @Test
    public void BuilderAccountIdTest() {
        RequestDTO user = new RequestDTO.Builder()
                .accountId(1)
                .build();
        assertEquals(1, user.getAccountId());
    }

    @Test
    public void BuilderStatusTest() {
        RequestDTO user = new RequestDTO.Builder()
                .status("status")
                .build();
        assertEquals("status", user.getStatus());
    }

    @Test
    public void BuilderAccountNameTest() {
        RequestDTO user = new RequestDTO.Builder()
                .accountName("name")
                .build();
        assertEquals("name", user.getAccountName());
    }

    @Test
    public void BuilderPaymentCountTest() {
        RequestDTO user = new RequestDTO.Builder()
                .paymentCount(12)
                .build();
        assertEquals(12, user.getPaymentCount());
    }




}
