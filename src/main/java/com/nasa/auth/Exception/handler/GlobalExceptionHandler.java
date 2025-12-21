package com.nasa.auth.Exception.handler;

import com.nasa.auth.DTO.ErrorResponse;
import com.nasa.auth.Exception.BaseException;
import com.nasa.auth.Exception.InvalidUserExeption;
import com.nasa.auth.Exception.UnAuthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ExceptionHandler(value ={ InvalidUserExeption.class, UnAuthorizedAccessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidUserException(BaseException baseException){
        return this.getErrorResponse(baseException);
    }

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentException(MethodArgumentNotValidException methodArgumentNotValidException){
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();

        String message = "Invalid data provided";
        if(fieldError!=null){
            message = fieldError.getDefaultMessage();
        }
        return new ErrorResponse("",message,LocalDateTime.now());
    }

   private ErrorResponse getErrorResponse(BaseException baseException){
      String message = messageSource.getMessage(baseException.getErrorCode(),null,baseException.getMessage(), Locale.ENGLISH);
      if(message==null){
          message = "Unable to proceed your process : Contact Admin";
      }
      return new ErrorResponse(baseException.getErrorCode(), message,LocalDateTime.now());
   }

}
