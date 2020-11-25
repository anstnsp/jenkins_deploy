package com.example.ssl.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.example.ssl.domain.Role;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
  @Value("${spring.jwt.secret}")
  private String secretKey;

  // 토큰유효시간 30분
  private long tokenValidTime = 30 * 60 * 1000L;

  private final UserDetailsService userDetailsService;

  // 객체 초기화, secretKey를 Base64로 인코딩한다.
  @PostConstruct
  protected void init() {
    System.out.println("@@@:"+secretKey);
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  // JWT토큰 생성
  public String createToken(String userPk, Role roles) {
    Claims claims = Jwts.claims().setSubject(userPk); // jwt payload에 저장되는 정보단위
    claims.put("roles", roles.getKey()); // 정보는 key / value 쌍으로 저장된다.
    Date now = new Date();
    return Jwts.builder().setClaims(claims)// 정보저장
        .setIssuedAt(now) // 토큰 발행시간.
        .setExpiration(new Date(now.getTime() + tokenValidTime)) // 만료시간
        .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 알고리즘 및 서명에 들어갈 secret값 셋팅
        .compact();
  }

  // JWT 토큰에서 인증 정보 조회
  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // 토큰에서 회원정보추출
  public String getUserPk(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
  public String resolveToken(HttpServletRequest request) {
    System.out.println("받은 토큰값: "+ request.getHeader("X-AUTH-TOKEN"));
    return request.getHeader("X-AUTH-TOKEN");
  }

  // 토큰의 유효성 + 만료일자 확인
  public boolean validateToken(String jwtToken) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }
  
}