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
import com.example.ssl.web.dto.request.sign.SignInRequestDTO;
import com.example.ssl.web.dto.request.sign.SignupRequestDTO;
import com.example.ssl.web.dto.response.common.CommonResult;
import com.example.ssl.web.dto.response.common.ResponseService;
import com.example.ssl.web.dto.response.common.SingleResult;

@RequiredArgsConstructor
@CustomBaseControllerAnnotation
public class SignController {

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;
  private final ResponseService responseService; 


  // 회원가입
  @PostMapping("/signup")
  public CommonResult signUp(@RequestBody SignupRequestDTO signupRequestDTO) {
      //해당 이메일이 존재하는지 체크. 
      userRepository.findByEmail(signupRequestDTO.getEmail()).ifPresent(
          u -> { throw new CUserExistException(); }
      );

      userRepository.save(User.builder()
              .email(signupRequestDTO.getEmail())
              .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
              .name(signupRequestDTO.getName())
              .role(Role.USER) // 최초 가입시 USER 로 설정
              .build());

      return responseService.getSuccessResult(); 
  }

  // 로그인
  @PostMapping("/signin")
  public SingleResult<String> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
      User user = userRepository.findByEmail(signInRequestDTO.getEmail())
              .orElseThrow(() -> new CUserNotFoundException("가입되지 않은 E-MAIL 입니다."));

      if (!passwordEncoder.matches(signInRequestDTO.getPassword(), user.getPassword())) {
          throw new CPasswordNotMatchedException("잘못된 비밀번호입니다.");
      }

      return responseService.getSingleResult(jwtTokenProvider.createToken(user.getEmail(), user.getRole()));
  }

}