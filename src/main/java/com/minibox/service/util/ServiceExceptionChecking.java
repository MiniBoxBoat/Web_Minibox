package com.minibox.service.util;

import com.minibox.exception.ParameterException;
import com.minibox.exception.ServerException;
import com.minibox.po.UserPo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.minibox.constants.BoxSize.LARGE;
import static com.minibox.constants.BoxSize.SMALL;
import static com.minibox.constants.ExceptionMessage.*;
import static com.minibox.constants.Constants.*;

public class ServiceExceptionChecking {

    public static void checkSqlExcute(boolean flag) {
        if (!flag) {
            throw new ServerException();
        }
    }

    public static void checkPhoneNumberIsUsed(UserPo user) {
        if (user != null) {
            throw new ParameterException(PHONE_NUMBER_IS_USED);
        }
    }

    public static void checkUserNameIsUsed(UserPo user) {
        if (user != null) {
            throw new ParameterException(USER_NAME_IS_USED);
        }
    }

    public static void checkUserNameIsTooLong(String userName) {
        if (userName.length() > 10) {
            throw new ParameterException(USER_NAME_IS_TOO_LONG);
        }
    }

    public static void checkPhoneNumberIsTrue(String phoneNumber){
        if (!FormatUtil.isPhoneNumberLegal(phoneNumber)){
            throw new ParameterException(PHONE_NUMBER_IS_NOT_TRUE);
        }
    }

    public static void checkPasswordIsTooShortOrToolLong(String password){
        if (password.length()<5 ){
            throw new ParameterException(PASSWORD_IS_TOO_SHORT);
        }
        if (password.length() > 20){
            throw new ParameterException((PASSWORD_IS_TOO_LONG));
        }
    }

    public static void checkSexIsTrue(String sex){
        if(!sex.equals(MALE) && !sex.equals(FEMALE)){
            throw new ParameterException(SEX_NOT_TRUE );
        }
    }

    public static void checkTimeIsInPattern(String time){
        if (!FormatUtil.isTimePattern(time)) {
            throw new ParameterException(TIME_PATTERN_IS_WRONG);
        }
    }

    public static void checkTimeIsAfterNow(String time){
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(TIME_PATTERN));
        LocalDateTime dateNow = LocalDateTime.now();
        if (!dateTime.isAfter(dateNow)){
            throw new ParameterException(TIME_IS_WRONG);
        }
    }

    public static void checkBoxSizeIsTrue(String boxSize) {
        if (!boxSize.equals(SMALL.size()) && !boxSize.equals(LARGE.size())) {
            throw new ParameterException(BOX_SIZE_NOT_TRUE);
        }
    }


}
