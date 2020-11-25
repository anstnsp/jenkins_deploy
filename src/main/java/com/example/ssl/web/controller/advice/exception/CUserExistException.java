package com.example.ssl.web.controller.advice.exception;

public class CUserExistException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = -372745554368264848L;

  public CUserExistException() {
    super();
  }
  public CUserExistException(String msg) {
    super(msg); 
  }

  public CUserExistException(String msg, Throwable cause) {
    super(msg, cause);
  }
}