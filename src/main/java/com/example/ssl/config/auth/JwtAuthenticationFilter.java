package com.example.ssl.config.auth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 해당 jwt 필터는 UsernamePasswordAuthenticationFilter 전에 실행 된다. 
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  // Request로 들어오는 JWT 토큰의 유효성을 검증하는 필터를 필터에 등록. 
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      // 헤더에서 JWT 를 받아옵니다.
      String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
      // 유효한 토큰인지 확인합니다.
      if (token != null && jwtTokenProvider.validateToken(token)) {
          // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
          Authentication authentication = jwtTokenProvider.getAuthentication(token);
          // SecurityContext 에 Authentication 객체를 저장합니다.
          SecurityContextHolder.getContext().setAuthentication(authentication);
      }
      chain.doFilter(request, response);
  }

}