package com.nasa.auth.Exception;

public class UnAuthorizedAccessException extends BaseException{

    public UnAuthorizedAccessException(String errorCode){
        super(errorCode);
    }
    public UnAuthorizedAccessException(String message,String errorCode){
        super(message,errorCode);
    }
}
