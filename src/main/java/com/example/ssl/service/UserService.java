package com.example.ssl.service;

import com.example.ssl.domain.User;
import com.example.ssl.domain.UserRepository;
import com.example.ssl.web.controller.advice.exception.CUserNotFoundException;
import com.example.ssl.web.dto.request.user.UserInfoModiDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
  
  private final UserRepository userRepository; 
  
  @Transactional
  public void updateUserInfo(UserInfoModiDTO userInfoModiDTO) {
    User user = userRepository.findByEmail(userInfoModiDTO.getEmail()).orElseThrow(CUserNotFoundException::new);
    user.update(userInfoModiDTO);
  }
  
}