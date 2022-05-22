package com.whlinks.e_commerce.utils;

import java.util.regex.Pattern;

public class Validations {

 public static boolean isValidEmail(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

 public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return Pattern.compile(regex)
                .matcher(password)
                .matches();
    }
}
