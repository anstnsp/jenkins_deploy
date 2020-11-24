package com.example.ssl.web.dto.user;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserLoginDTO {

  private String email; 
  private String password; 

}