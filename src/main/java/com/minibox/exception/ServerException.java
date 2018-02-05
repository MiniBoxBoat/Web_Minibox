package com.minibox.exception;

public class ServerException extends RuntimeException {

    private static String MSG= "服务器错误";
    public static int STATUS = 500;

    public ServerException(){
        super(MSG);
        this.printStackTrace();
    }
}
