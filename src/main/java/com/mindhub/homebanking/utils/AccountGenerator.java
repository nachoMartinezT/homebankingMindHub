package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.models.Client;

public class AccountGenerator {

    public static String generateAccountNumber(Long id){
        String serialNumber = "";
        while((serialNumber.length() + id.toString().length()) < 8 ){
        serialNumber += 0;
        }
        serialNumber += id;
        return "VIN" + serialNumber;
    }
}
