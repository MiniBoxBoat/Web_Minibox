package com.minibox.exception;

public class ServerException extends RuntimeException {

    public static String msg = "服务器错误";

    public ServerException(){
        super(msg);
        this.printStackTrace();
    }
}
