package com.minibox.exception;

public class SendSmsFailedException extends RuntimeException {
    private static String MSG = "发送验证码失败";
    public static int STATUS = 500;

    public SendSmsFailedException(){
        super(MSG);
    }

}
