package com.example.ssl.web.controller;

import com.example.ssl.common.CustomBaseControllerAnnotation;
import com.example.ssl.config.auth.JwtTokenProvider;
import com.example.ssl.config.auth.WebSecurityConfig;
import com.example.ssl.domain.Role;
import com.example.ssl.domain.User;
import com.example.ssl.domain.UserRepository;
import com.example.ssl.web.dto.response.common.ResponseService;
import com.example.ssl.web.dto.response.common.SingleResult;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import lombok.extern.slf4j.Slf4j;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import javax.print.attribute.standard.Media;



@Slf4j

@WebMvcTest(controllers = AdminControllerTest.class,
                          
        excludeFilters = @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = WebSecurityConfig.class
        ))
public class UserControllerTest {

  @Autowired
  private MockMvc mvc; 

  @MockBean
  private JwtTokenProvider jwtTokenProvider; 
  @MockBean
  private ResponseService responseService; 

  @MockBean
  private UserRepository userRepository; 
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
  
  @WithMockUser(username = "mockUser", roles = {"USER"})
  @Test
  public void 내정보보기() throws Exception {
    //given 
    // Authentication authentication = jwtTokenProvider.getAuthentication(token);
    // SecurityContext 에 Authentication 객체를 저장합니다.
    String email = "anstnsp@naver.com"; 
    String password = "123"; 
    String name = "이름"; 

    User user = User.builder().email(email).name(name).password(password).role(Role.USER).build(); 
    SingleResult<User> result = new SingleResult<User>(); 
    result.setCode(0);
    result.setData(user);
    result.setMsg("성공");
    result.setSuccess(true);
    given(userRepository.findByEmail(email)).willReturn(Optional.ofNullable(user)); 
    given(responseService.getSingleResult(user)).willReturn(result);

    ResultActions actions = mvc.perform(get("/v1/users/me")
                            .contentType(MediaType.APPLICATION_JSON))
                            .andDo(print()); 

    //then 
    actions
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("이름"))
            .andDo(print());

    
  }
}