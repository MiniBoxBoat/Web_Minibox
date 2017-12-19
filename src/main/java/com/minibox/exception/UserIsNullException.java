package com.minibox.exception;

public class UserIsNullException extends Exception{
    private static String msg = "请检查用户名和密码是否出入正确";

    public UserIsNullException(){
        super(msg);
    }
}
