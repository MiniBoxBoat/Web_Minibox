package com.minibox.exception;

public class TakenVerifyException extends RuntimeException {
    private static String MSG = "用户认证错误";
    public static int STATUS = 401;

    public TakenVerifyException(){
        super(MSG);
    }
}
