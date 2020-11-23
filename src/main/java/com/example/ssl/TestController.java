package com.example.ssl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


  @PostMapping("/anstnsp")
  public String ssltest() {
    System.out.println("들어옴");
    return "ssl success";
  }
  
}