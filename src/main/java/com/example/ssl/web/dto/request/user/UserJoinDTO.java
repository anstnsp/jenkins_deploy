package com.example.ssl.web.dto.request.user;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserJoinDTO {

  private String email; 
  private String password; 
  private String name; 
  
}