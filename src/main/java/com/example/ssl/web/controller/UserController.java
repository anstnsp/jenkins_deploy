package com.example.ssl.web.controller;

import com.example.ssl.common.CustomBaseControllerAnnotation;
import com.example.ssl.domain.User;
import com.example.ssl.domain.UserRepository;
import com.example.ssl.service.UserService;
import com.example.ssl.web.controller.advice.exception.CUserNotFoundException;
import com.example.ssl.web.dto.request.user.UserInfoModiDTO;
import com.example.ssl.web.dto.response.common.CommonResult;
import com.example.ssl.web.dto.response.common.ListResult;
import com.example.ssl.web.dto.response.common.ResponseService;
import com.example.ssl.web.dto.response.common.SingleResult;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CustomBaseControllerAnnotation
public class UserController {
  
  private final UserRepository userRepository; 
  private final UserService userService; 
  private final ResponseService responseService; 

  @GetMapping("/users/me")
  public SingleResult<User> findMe(@RequestParam String lang) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName(); //회원가입한 이메일을 반환한다. 
    return responseService.getSingleResult(userRepository.findByEmail(email).orElseThrow(CUserNotFoundException::new));
  }

  @PutMapping("/users")
  public CommonResult modifyUser(@RequestBody UserInfoModiDTO userInfoModiDTO) {
    userService.updateUserInfo(userInfoModiDTO); 
    return responseService.getSuccessResult();
  }

  @DeleteMapping("/users/{id}")
  public CommonResult deleteUser(@PathVariable long id) {
    userRepository.deleteById(id);
    return responseService.getSuccessResult();
  }



}
