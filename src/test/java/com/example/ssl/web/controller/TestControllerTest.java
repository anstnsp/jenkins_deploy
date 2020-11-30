package com.example.ssl.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ssl.config.auth.WebSecurityConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class TestControllerTest {

  @Autowired
  TestRestTemplate restTemplate; 

  @Test
  public void 헬로테스트() throws Exception {
    //given
    String expectedValue = "hello"; 
    //when    
    String realValue = restTemplate.getForObject("/test", String.class);
    //then 
    assertEquals(expectedValue, realValue);

  }
}