package com.ebanking.util;

import java.util.Random;

public class AccountNum {
    public static Long getRndNumber() {
        Random random = new Random();
        Long randomNumber = 0L;
        boolean loop = true;
        while (loop) {
            randomNumber = random.nextLong();
            if (Long.toString(randomNumber).length() == 10 && !Long.toString(randomNumber).startsWith("-")) {
                loop = false;
            }
        }
        return randomNumber;
    }
}
