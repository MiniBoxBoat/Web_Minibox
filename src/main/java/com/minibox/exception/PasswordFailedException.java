package com.minibox.exception;

public class PasswordFailedException extends Exception{

    public static String msg = "密码错误";

    public PasswordFailedException(){
        super(msg);
    }
}
