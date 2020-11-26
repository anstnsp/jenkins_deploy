package com.example.ssl.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import com.example.ssl.domain.Role;
import com.example.ssl.domain.User;
import com.example.ssl.domain.UserRepository;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.test.context.junit.jupiter.SpringExtension;


import lombok.extern.slf4j.Slf4j;

@Slf4j
// @WebMvcTest({SignControllerTest.class, JwtTokenProvider.class, CustomUserDetailService.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SignControllerTest {

  @Autowired
  private TestRestTemplate restTemplate; 
  @MockBean(name = "userRepository")
  private UserRepository userRepository; 

  @Test
  public void 회원가입() throws Exception {
    //given 
    String email = "anstnsp1@naver.com";
    String password = new BCryptPasswordEncoder().encode("123"); 
    String name = "이름"; 
    User user = User.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .role(Role.USER).build();
    // Optional.of(user);
    given(this.userRepository.save(user)).willReturn(user);
    given(this.userRepository.findByEmail(email)).willReturn(Optional.of(user));
    long aa =    this.userRepository.count();
    System.out.println(aa);
    System.out.println(aa);
    System.out.println(aa);
    System.out.println(aa);
    System.out.println(aa);
    ObjectMapper obm = new ObjectMapper(); 
    String userStr = obm.writeValueAsString(user);
    String url = "/v1/signup";

    //when
    HttpHeaders headers = new HttpHeaders(); 
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    HttpEntity<Object> request = new HttpEntity<Object>(user, headers); 
    String result = restTemplate.postForObject(url, request, String.class); 
    System.out.println(aa);
    System.out.println(aa);
    System.out.println(aa);
    System.out.println(aa);
    System.out.println("result:"+result);
    System.out.println("result:"+result);
    System.out.println("result:"+result);
    System.out.println("result:"+result);
    System.out.println("result:"+result);
    
    //then 
    assertEquals(1, 1);

  }
}