package com.nasa.auth.exception;

public class InvalidUserExeption extends BaseException{

    public InvalidUserExeption(String message, String errorCode) {
        super(message,errorCode);
    }
    public InvalidUserExeption(String errorCode){
        super(errorCode);
    }
}
