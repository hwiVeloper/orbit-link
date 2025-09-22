package dev.hwiveloper.orbitlink.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,16}$";
    public static boolean validatePassword(String password) {
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }

        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
