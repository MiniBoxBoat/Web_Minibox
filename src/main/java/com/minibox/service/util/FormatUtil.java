package com.minibox.service.util;

/**
 * @author MEI
 */
public class FormatUtil {

    public static boolean isPhoneNumberLegal(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        return str.matches(regExp);
    }

    public static boolean isEmail(String email) {
        String regExp = "^(\\w+((-\\w+)|(\\.\\w+))*)\\+\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$ ";
        return email.matches(regExp);
    }

    public static boolean isTimePattern(String time) {
        String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        return time.matches(time);
    }
}
