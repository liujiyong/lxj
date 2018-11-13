package com.lxj.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author admin
 */
public final class SafeHashGenerator {

    private static final int STRETCH_COUNT = 1000;

    /*
     * salt＋hash password
     */
    public static String getSaltedPassword(String password, String userId) {
        String salt = getSha256(userId);
        return getSha256(salt + password);
    }

    /*
     * salt + hash password
     */
    public static String getStretchedPassword(String password, String userId) {
        String salt = getSha256(userId);
        String hash = "";

        for (int i = 0; i < STRETCH_COUNT; i++) {
            hash = getSha256(hash + salt + password);
        }

        return hash;
    }

    /*
     * get string SHA256 hash value
     */
    private static String getSha256(String target) {
        MessageDigest md = null;
        StringBuffer buf = new StringBuffer();
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(target.getBytes());
            byte[] digest = md.digest();

            for (int i = 0; i < digest.length; i++) {
                buf.append(String.format("%02x", digest[i]));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return buf.toString();
    }
}
