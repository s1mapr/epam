package com.my.service;

import com.my.dao.AccountDAO;
import com.my.dao.CardDAO;

public class CardService {
    public static void updateAmount(double amount, int cardId){
        CardDAO.updateAmount(amount, cardId);
    }

    public static double getAmount(int cardId){
        return CardDAO.getAmount(cardId);
    }

    public static int createCard(String cardNumber,String date,String cvv){
        return  CardDAO.createNewCard(cardNumber, date, cvv);
    }

    public static boolean checkCardNumberIfExist(String cardNumber){
        return CardDAO.checkCardNumber(cardNumber);
    }
}
