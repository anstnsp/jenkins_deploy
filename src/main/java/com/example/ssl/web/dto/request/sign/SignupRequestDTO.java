package com.example.ssl.web.dto.request.sign;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class SignupRequestDTO {
  //@NotEmpty 는 null 과 길이0 둘다 허용하지 않음. 
  //@NotNull 은 null만 비허용. 
  @NotEmpty(message = "이메일은 필수값 입니다.")
  private String email; 
  @NotEmpty(message = "비밀번호는 필수값 입니다.")
  private String password; 
  @NotEmpty(message = "이름은 필수값 입니다.")
  private String name; 
  
  private String gender; 
  
}