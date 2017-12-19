package com.minibox.exception;

public class TakenVirifyException extends Exception {
    public static String msg = "用户认证错误";

    public TakenVirifyException(){
        super(msg);
    }
}
