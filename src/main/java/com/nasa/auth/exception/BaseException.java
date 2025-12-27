package com.nasa.auth.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException{

    private String errorCode;
  BaseException(String message,String errorCode){
      super(message);
      this.errorCode=errorCode;
  }
  BaseException(String errorCode){
      this.errorCode=errorCode;
  }

}
