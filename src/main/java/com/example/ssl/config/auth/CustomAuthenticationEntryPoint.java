package com.example.ssl.config.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * JWT토큰에 대한 에러일 경우 필터링의 순서 (스프링시큐리티가 DispatcherServlet도달전 필터로 가로챔) 때문에 
 * ControllerAdvice에서 예외 처리를 할 수 없어서 AuthenticationEntryPoint를 재정의해서 예외 처리 해야함. 
 * 1.JWT토큰 없이 API 호출하였을 경우 
 * 2.형식에 맞지 않거나 만료된 JWT토큰으로 API호출한 경우 
 * 3.정상적인JWT토큰으로 API를 호출 하였으나 해당 리소스에 권한 없을경우. 
 * 
 * -> 1,2인 경우 error,message에 Forbidden, Access Denied 뜨고 
 * -> 3인 경우 Forbidden, Forbidden 뜬다. 
 * 
 * ===== 해당 CustomAuthenticationEntryPoint은 1,2번인 경우 처리 ===== 
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
        response.sendRedirect("/exception/entrypoint");
  }

}