package com.example.ssl.web.controller.advice.exception;

public class CUserNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -801608168736895198L;

  public CUserNotFoundException() {

  }
  
  public CUserNotFoundException(String msg) {
    super(msg); 
  }

  public CUserNotFoundException(String msg, Throwable t) {
    super(msg, t);   
  }

}

