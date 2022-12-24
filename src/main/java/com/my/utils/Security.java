package com.my.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Security util
 */
public class Security {
    private static final Logger log = Logger.getLogger(Security.class);

    /**
     * hashing password
     */
    public static String hashPassword(final String password) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            log.info("codding password");
        } catch (NoSuchAlgorithmException e) {
            log.error("problem with codding password");
            log.error("Exception -  " + e);
        }
        byte[] bytes = md5.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
