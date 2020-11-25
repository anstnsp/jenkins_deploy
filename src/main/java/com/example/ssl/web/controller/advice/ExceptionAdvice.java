package com.example.ssl.web.controller.advice;

import javax.servlet.http.HttpServletRequest;

import com.example.ssl.web.controller.advice.exception.CAuthenticationEntryPointException;
import com.example.ssl.web.controller.advice.exception.CUserExistException;
import com.example.ssl.web.controller.advice.exception.CUserNotFoundException;
import com.example.ssl.web.dto.response.common.CommonResult;
import com.example.ssl.web.dto.response.common.ResponseService;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

  private final ResponseService responseService; 
	private final MessageSource messageSource; 

  //아래예외 제외한 모든 예외 처리 
  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) //INTERNAL_SERVER_ERROR : 500 
  protected CommonResult defaultException(HttpServletRequest request, Exception e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
  }

  //해당 유저 존재 x
  @ExceptionHandler({CUserNotFoundException.class, UsernameNotFoundException.class})
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) 
  protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
  }

  //회원가입할 때 이메일 이미 존재할 때 
  @ExceptionHandler(CUserExistException.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) 
  protected CommonResult userExistException(HttpServletRequest request, CUserExistException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("userExist.code")), getMessage("userExist.msg"));
  }

  //JWT 토큰 잘못됐거나 없음. 
  @ExceptionHandler(CAuthenticationEntryPointException.class)
  @ResponseStatus(code = HttpStatus.FORBIDDEN)
  protected CommonResult notAuthenticatedException(HttpServletRequest request, CAuthenticationEntryPointException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
  }


  //code정보에 해당하는 메시지를 조회.
  private String getMessage(String code) {
    return getMessage(code, null);
  }

  //code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회.
  private String getMessage(String code, Object[] args) {
      return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
  }


}