package com.mindhub.homebanking.utils;

import java.util.Random;

public class NumberGenerator {
    static Random random = new Random();


    public static String accountNumberGenerator() {
        return "VIN" + String.format("%08d", random.nextInt(99999999) + 1);
    }

    public static String cardNumberGenerator() {
        String numbers = "";

        // Genera 16 números aleatorios
        for (int i = 0; i < 4; i++) {
            int number = random.nextInt(9999) + 1;
            String formattedNumber = String.format("%04d", number);
            numbers += formattedNumber + " ";
        }
        return numbers;
    }

    public static int cvvNumberGenerator() {
        int number = random.nextInt(999) + 1;
        String formattedNumber = String.format("%03d", number);
        return Integer.parseInt(formattedNumber);
    }
}
