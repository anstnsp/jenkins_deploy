package com.example.ssl.web.controller;

import com.example.ssl.config.auth.JwtTokenProvider;
import com.example.ssl.domain.Role;
import com.example.ssl.domain.User;
import com.example.ssl.web.dto.response.common.ResponseService;
import com.example.ssl.web.dto.response.common.SingleResult;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@Slf4j
@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mvc; 

  @MockBean
  private JwtTokenProvider jwtTokenProvider; 
  @MockBean
  private ResponseService responseService; 

  // private final UserRepository userRepository; 
  // private final UserService userService; 
  // private final ResponseService responseService; 


  @Test
  public void 토큰생성() {
    //given 
    String email = "anstnsp@naver.com"; 
    String password = "123"; 
    String name = "이름"; 
    Role role = Role.USER;
    User user = User.builder().email(email).password(password).name(name).role(role).build();
    SingleResult<User> result = new SingleResult<User>(); 
    result.setCode(0);
    result.setSuccess(true);
    result.setData(user);
    given(jwtTokenProvider.createToken(email, role)).willReturn("jwtToken"); 
    given(responseService.getSingleResult(user)).willReturn(result); 
    //when 
    String token = jwtTokenProvider.createToken(email, role);
    //then 
    assertEquals("jwtToken", token);

  }
  
}