package com.example.ssl.web.controller;

import com.example.ssl.common.CustomBaseControllerAnnotation;
import com.example.ssl.domain.User;
import com.example.ssl.domain.UserRepository;
import com.example.ssl.web.controller.advice.exception.CUserNotFoundException;
import com.example.ssl.web.dto.response.common.ListResult;
import com.example.ssl.web.dto.response.common.ResponseService;
import com.example.ssl.web.dto.response.common.SingleResult;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CustomBaseControllerAnnotation
public class AdminController {

  private final UserRepository userRepository; 
  private final ResponseService responseService; 

  //관리자가 유저정보 하나 보기 
  @GetMapping("/admin/users/{email}")
  public SingleResult<User> findUserByEmail(@PathVariable String email) {
    User user = userRepository.findByEmail(email).orElseThrow(CUserNotFoundException::new);
    return responseService.getSingleResult(user);
  }

  @GetMapping("/admin/users")
  public ListResult<User> findAllUsers() {
    return responseService.getListResult(userRepository.findAll());
  }

}