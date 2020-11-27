package com.example.ssl.web.dto.request.sign;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequestDTO {
  @NotEmpty(message = "이메일은 필수값 입니다.")
  private String email; 
  @NotEmpty(message = "비밀번호는 필수값 입니다.")
  private String password; 
}