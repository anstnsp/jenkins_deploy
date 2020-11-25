package com.example.ssl.web.controller.advice.exception;

import com.example.ssl.web.dto.response.common.CommonResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

  @GetMapping("/entrypoint")
  public CommonResult entrypointException() {
    throw new CAuthenticationEntryPointException(); 
  }
  
}