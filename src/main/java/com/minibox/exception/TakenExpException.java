package com.minibox.exception;

public class TakenExpException extends  Exception {
    public static String msg="用户认证错误";

    public TakenExpException(){
        super(msg);
    }
}
