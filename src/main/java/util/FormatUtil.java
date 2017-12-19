package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author MEI
 */
public class FormatUtil {

    public static boolean isPhoneNumberLegal(String str){
        String regExp= "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isEmail(String email){
        String regExp="^(\\w+((-\\w+)|(\\.\\w+))*)\\+\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$ ";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
