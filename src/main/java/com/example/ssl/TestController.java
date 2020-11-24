package com.example.ssl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


  @PostMapping("/anstnsp")
  public TestDto ssltest(@RequestBody String body) {
    String response = body; 
    TestDto dto = new TestDto();
    dto.setLevel(1);
    dto.setNum(33);
    dto.setStr("스트링입니다.");

    // Integer a = new Integer(100.123); 
    // System.out.println("인티저값:"+a);
    System.out.println("body:"+body);
    System.out.println("response:"+ response);
    System.out.println("들어옴");
    return dto;
  }
  
}