package com.example.ssl.web.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

import com.example.ssl.common.CustomBaseControllerAnnotation;
import com.example.ssl.config.auth.JwtTokenProvider;
import com.example.ssl.domain.Role;
import com.example.ssl.domain.User;
import com.example.ssl.domain.UserRepository;
import com.example.ssl.web.controller.advice.exception.CPasswordNotMatchedException;
import com.example.ssl.web.controller.advice.exception.CUserExistException;
import com.example.ssl.web.controller.advice.exception.CUserNotFoundException;
import com.example.ssl.web.dto.request.user.UserJoinDTO;
import com.example.ssl.web.dto.request.user.UserLoginDTO;

@RequiredArgsConstructor
@CustomBaseControllerAnnotation
public class SignController {

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  // 회원가입
  @PostMapping("/signup")
  public Long join(@RequestBody UserJoinDTO user) {
      //해당 이메일이 존재하는지 체크. 
      userRepository.findByEmail(user.getEmail()).ifPresent(
          u -> { throw new CUserExistException(); }
      );

      return userRepository.save(User.builder()
              .email(user.getEmail())
              .password(passwordEncoder.encode(user.getPassword()))
              .name(user.getName())
              .role(Role.USER) // 최초 가입시 USER 로 설정
              .build()).getId();
  }

  // 로그인
  @PostMapping("/signin")
  public String login(@RequestBody UserLoginDTO user) {
      User member = userRepository.findByEmail(user.getEmail())
              .orElseThrow(() -> new CUserNotFoundException("가입되지 않은 E-MAIL 입니다."));

      if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
          throw new CPasswordNotMatchedException("잘못된 비밀번호입니다.");
      }

      return jwtTokenProvider.createToken(member.getEmail(), member.getRole());
  }

}