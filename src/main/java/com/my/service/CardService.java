package com.my.service;

import com.my.dao.CardDAO;
/**
 * Card service
 */
public class CardService {
    /**
     * updates amount of card
     */
    public static void updateAmount(double amount, int cardId){
        CardDAO.updateAmount(amount, cardId);
    }
    /**
     * Returns card amount
     */
    public static double getAmount(int cardId){
        return CardDAO.getAmount(cardId);
    }

    /**
     * Creates new entry in database that has card information
     */

    public static int createCard(String cardNumber,String date,String cvv){
        return  CardDAO.createNewCard(cardNumber, date, cvv);
    }

    /**
     * Checks card in database for existence
     */

    public static boolean checkCardNumberIfExist(String cardNumber){
        return CardDAO.checkCardNumber(cardNumber);
    }
}
