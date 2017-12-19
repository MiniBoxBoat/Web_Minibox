package com.minibox.exception;

public class SendSmsFailedException extends Exception {
    private static String msg = "发送验证码失败";

    public SendSmsFailedException(){
        super(msg);
    }
}
