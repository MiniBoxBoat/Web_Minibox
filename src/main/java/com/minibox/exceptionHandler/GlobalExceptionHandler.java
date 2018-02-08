package com.minibox.exceptionHandler;

import com.aliyuncs.exceptions.ClientException;
import com.minibox.dto.ResponseEntity;
import com.minibox.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static com.minibox.constants.ExceptionMessage.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final int SERVER_EXCEPTION_CODE = 500;
    public static final int CLIENT_EXCEPTION_CODE = 400;
    public static final int RESOURCE_NOT_FOUND_EXCEPTION_CODE = 404;

    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<Object> handlerParameterException(ParameterException e){
        e.printStackTrace();
        return new ResponseEntity<>(ParameterException.STATUS, e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<Object> handlerRollbackException(RollbackException e){
        e.printStackTrace();
        return new ResponseEntity<>(RollbackException.STATUS,e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(SendSmsFailedException.class)
    public ResponseEntity<Object> handlerSendSmsFailedException(SendSmsFailedException e){
        e.printStackTrace();
        return new ResponseEntity<>(SendSmsFailedException.STATUS,e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Object> handlerServerException(ServerException e){
        e.printStackTrace();
        return new ResponseEntity<>(ServerException.STATUS,e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(TokenVerifyException.class)
    public ResponseEntity<Object> handlerTokenVerifyException(TokenVerifyException e){
        e.printStackTrace();
        return new ResponseEntity<>(TokenVerifyException.STATUS,AUTHENTICATION_FAILURE, e.getStackTrace());
    }

    @ExceptionHandler(VerifyCodeException.class)
    public ResponseEntity<Object> handlerVerifyCodeException(VerifyCodeException e){
        e.printStackTrace();
        return new ResponseEntity<>(VerifyCodeException.STATUS,e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Object> handlerClientException(ClientException e){
        e.printStackTrace();
        return new ResponseEntity<>(CLIENT_EXCEPTION_CODE,"客户端请求错误", e.getStackTrace());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handlerNullPointerException(NullPointerException e){
        e.printStackTrace();
        return new ResponseEntity<>(RESOURCE_NOT_FOUND_EXCEPTION_CODE, e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handlerResourceNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(ResourceNotFoundException.STATUS, e.getMessage(), e.getStackTrace());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(SERVER_EXCEPTION_CODE, "未知错误", e.getStackTrace());
    }

}
