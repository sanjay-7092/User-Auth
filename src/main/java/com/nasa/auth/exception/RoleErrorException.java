package com.nasa.auth.exception;

public class RoleErrorException extends BaseException{

    public RoleErrorException(String message,String errorCode){
        super(message,errorCode);
    }

    public RoleErrorException(String errorCode){
        super(errorCode);
    }
}
