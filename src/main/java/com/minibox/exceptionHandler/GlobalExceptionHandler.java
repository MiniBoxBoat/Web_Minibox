package com.minibox.exceptionHandler;

import com.aliyuncs.exceptions.ClientException;
import com.minibox.dto.ResponseEntity;
import com.minibox.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.ws.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<Object> handlerParameterException(ParameterException e){
        e.printStackTrace();
        return new ResponseEntity<>(e.getStatus(), e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<Object> handlerParameterException(RollbackException e){
        e.printStackTrace();
        return new ResponseEntity<>(RollbackException.STATUS,e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(SendSmsFailedException.class)
    public ResponseEntity<Object> handlerParameterException(SendSmsFailedException e){
        e.printStackTrace();
        return new ResponseEntity<>(SendSmsFailedException.STATUS,e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Object> handlerParameterException(ServerException e){
        e.printStackTrace();
        return new ResponseEntity<>(ServerException.STATUS,e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(TakenVerifyException.class)
    public ResponseEntity<Object> handlerParameterException(TakenVerifyException e){
        e.printStackTrace();
        return new ResponseEntity<>(TakenVerifyException.STATUS,e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(VerifyCodeException.class)
    public ResponseEntity<Object> handlerParameterException(VerifyCodeException e){
        e.printStackTrace();
        return new ResponseEntity<>(VerifyCodeException.STATUS,e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Object> handlerParameterException(ClientException e){
        e.printStackTrace();
        return new ResponseEntity<>(400,"客户端请求错误", e.getStackTrace());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handlerNullPointerException(NullPointerException e){
        e.printStackTrace();
        return new ResponseEntity<>(400, e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(500, "未知错误", e.getStackTrace());
    }

}
