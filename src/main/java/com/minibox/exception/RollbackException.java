package com.minibox.exception;

public class RollbackException extends RuntimeException {
    private static String MSG = "服务器错误";
    public static int STATUS = 500;

    public RollbackException(){
        super(MSG);
    }
}
