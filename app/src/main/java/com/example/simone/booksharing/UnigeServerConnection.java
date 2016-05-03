package com.example.simone.booksharing;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnigeServerConnection {
    public static final String URL = "http://webdev.dibris.unige.it/~S3940125/ANDROID_ENGINE/";

    public static final String LOGIN = "query_login.php";
    public static final String REGISTRATION = "query_registration.php";
    public static final String PSW_DIMENTICATA = "query_psw_dimenticata.php";


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


}
