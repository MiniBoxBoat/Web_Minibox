package com.minibox.exception;

public class VerifyCodeException extends Exception {

    private static String msg = "验证码错误";

    public VerifyCodeException(){
        super(msg);
    }
}
