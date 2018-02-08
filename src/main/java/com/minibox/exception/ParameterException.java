package com.minibox.exception;

public class ParameterException extends RuntimeException {
    public static final int STATUS = 400;
    public ParameterException(String msg){
        super(msg);
    }

}
