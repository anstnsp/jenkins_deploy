package com.example.ssl.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import com.example.ssl.config.auth.JwtTokenProvider;
import com.example.ssl.config.auth.WebSecurityConfig;
import com.example.ssl.domain.Role;
import com.example.ssl.domain.User;
import com.example.ssl.domain.UserRepository;
import com.example.ssl.web.dto.request.sign.SignupRequestDTO;
import com.example.ssl.web.dto.response.common.CommonResult;
import com.example.ssl.web.dto.response.common.ResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.AuthenticationManagerBeanDefinitionParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SignControllerTest {


  private MockMvc mvc; 

  @MockBean(name = "userRepository")
  private UserRepository userRepository; 
  @MockBean
  private JwtTokenProvider jwtTokenProvider; 
  private BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
  @MockBean
  private ResponseService responseService; 

  @Autowired
  private WebApplicationContext webApplicationConterxt; 

  @Test
  public void 회원가입() throws Exception {
    //given 
    String email = "anstnsp1@naver.com";
    String password ="123";
    String name = "이름"; 
    User user = User.builder()
                    .email(email)
                    .password(password)
                    .name(name).build();
    SignupRequestDTO dto = new SignupRequestDTO();
    dto.setEmail(email);
    dto.setName(name);
    dto.setPassword(password);

    CommonResult commonResult = new CommonResult();
    commonResult.setCode(0);
    commonResult.setSuccess(true);
    given(this.userRepository.save(user)).willReturn(user);
    given(this.userRepository.findByEmail(email)).willReturn(Optional.ofNullable(null));
    given(jwtTokenProvider.createToken(email, Role.USER)).willReturn("l2m3fo23mfoi2mopamofaipfmafa2");
    given(passwordEncoder.encode(password)).willReturn("###$$$");
    given(responseService.getSuccessResult()).willReturn(commonResult);

    //when
    ObjectMapper obm = new ObjectMapper(); 
    String jsonStr = obm.writeValueAsString(dto);
    ResultActions result = mvc.perform(post("/v1/signup").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                              .andDo(print());
    
    System.out.println("result:"+result);
    result.andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          // .andExpect(jsonPath($., matcher))
          .andDo(print());
    
    //then 
    assertEquals(1, 1);

  }
}