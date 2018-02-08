package com.minibox.exception;

public class ResourceNotFoundException extends RuntimeException {
    public static final int STATUS = 404;

    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
