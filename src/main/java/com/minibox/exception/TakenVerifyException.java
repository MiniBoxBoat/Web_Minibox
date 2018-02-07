package com.minibox.exception;

public class TakenVerifyException extends RuntimeException {
    public static int STATUS = 401;

    public TakenVerifyException(String msg){
        super(msg);
    }
}
