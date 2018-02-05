package com.minibox.exception;

public class VerifyCodeException extends RuntimeException {

    private static String msg = "验证码错误";
    public static int STATUS = 401;

    public VerifyCodeException(){
        super(msg);
    }
}
