package com.nasa.auth.Exception.handler;

import com.nasa.auth.DTO.ErrorResponse;
import com.nasa.auth.Exception.BaseException;
import com.nasa.auth.Exception.InvalidUserExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private final MessageSource messageSource;

    GlobalExceptionHandler(MessageSource messageSource){
        this.messageSource=messageSource;
    }

    @ExceptionHandler(InvalidUserExeption.class)
    public ErrorResponse handleInvalidUserException(BaseException baseException){
        return this.getErrorResponse(baseException);
    }

   private ErrorResponse getErrorResponse(BaseException baseException){
      String message = messageSource.getMessage(baseException.getErrorCode(),null,baseException.getMessage(), Locale.ENGLISH);
      if(message==null){
          message = "Unable to procced your process : Contact Admin";
      }
      return new ErrorResponse(baseException.getErrorCode(), message,LocalDateTime.now());
   }

}
