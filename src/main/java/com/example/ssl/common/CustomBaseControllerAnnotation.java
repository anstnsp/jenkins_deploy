package com.example.ssl.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 어노테이션의 목적은 주로 자바의 메타데이터로 사용하기 위함. 
 * 어노테이션의 작성법은 접근제한자 @interface 어노테이션명 { 내용 } 입니다.
 * @RequestMapping("/v1")
 **/ 

@Target(ElementType.TYPE) //어노테이션 적용 위치 
@Retention(RetentionPolicy.RUNTIME) //어노테이션 유지 정책 
@RestController
@RequestMapping("/v1")
public @interface CustomBaseControllerAnnotation {

}