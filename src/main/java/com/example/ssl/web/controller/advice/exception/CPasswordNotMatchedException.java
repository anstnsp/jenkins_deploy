package com.example.ssl.web.controller.advice.exception;

public class CPasswordNotMatchedException extends RuntimeException {

  private static final long serialVersionUID = -4706021796992478438L;

  public CPasswordNotMatchedException() {
    super(); 
  }

  public CPasswordNotMatchedException(String msg) {
    super(msg); 
  }

  public CPasswordNotMatchedException(String msg, Throwable cause) {
    super(msg, cause); 
  }
  
}