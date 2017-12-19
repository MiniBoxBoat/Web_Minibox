package com.minibox.exception;

public class UserNotExistException extends Exception {

    private static String msg = "用户不存在";

    public UserNotExistException(){
        super(msg);
    }
}
