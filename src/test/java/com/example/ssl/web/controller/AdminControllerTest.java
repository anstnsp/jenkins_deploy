package com.example.ssl.web.controller;

import com.example.ssl.config.auth.JwtTokenProvider;
import com.example.ssl.config.auth.WebSecurityConfig;
import com.example.ssl.domain.User;
import com.example.ssl.domain.UserRepository;
import com.example.ssl.web.dto.response.common.CommonResult;
import com.example.ssl.web.dto.response.common.ResponseService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.core.Is.is; 
import static org.mockito.BDDMockito.given; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; 
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

/**
 * @WebMvcTest
 *  - MVC를 위한 테스트, 컨트롤러가 예상대로 동작하는지 테스트하는데 사용된다.
 * @WebMvcTest 어노테이션을 사용시 다음 내용만 스캔 하도록 제한한다. (보다 가벼운 테스팅이 가능하다.)
 * @Controller, @ControllerAdvice, @JsonComponent, Converter, GenericConverter, Filter, HandlerInterceptor, WebMvcCon
 * MockBean, MockMVC를 자동 구성하여 테스트 가능하도록 한다.
 - Spring Security의 테스트도 지원 한다.
 */
@Slf4j
@WebMvcTest(controllers = AdminControllerTest.class,
        excludeFilters = @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = WebSecurityConfig.class
        ))
// @SpringBootTest
public class AdminControllerTest {

  @Autowired
  private MockMvc mvc; 
  
  @MockBean(name = "userRepository")
  private UserRepository userRepository; 
  @MockBean
  private ResponseService responseService; 

  @Test
  @WithMockUser(username = "mockUser", roles = {"ADMIN"})
  void 유저하나조회() throws Exception {
    //given 
    String email = "anstnsp@naver.com";
    String password = "123";
    String name = "이름"; 
    User user = User.builder().email(email).password(password).name(name).build();
    
    CommonResult commonResult = new CommonResult();
    commonResult.setCode(0);
    commonResult.setSuccess(true);
    
    given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
    given(responseService.getSuccessResult()).willReturn(commonResult);
    //when 
    ResultActions actions = mvc.perform(
                                get("/v1/admin/users/{email}", "anstnsp@naver.com")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(print());
    
        
    //then 
    actions.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("이름"))
            .andDo(print()); 
  }
}