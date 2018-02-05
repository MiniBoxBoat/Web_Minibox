package com.minibox.exception;

public class ParameterException extends RuntimeException {
    private int status;
    public ParameterException(String msg, int status){
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
