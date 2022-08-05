package ru.job4j.domain;

import java.security.SecureRandom;

public class Generator {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final int LEN = 7;
    static SecureRandom rnd = new SecureRandom();

    public String randomString() {
        StringBuilder sb = new StringBuilder(LEN);
        for (int i = 0; i < LEN; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
