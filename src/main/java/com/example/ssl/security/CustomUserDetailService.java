package com.example.ssl.security;

import com.example.ssl.domain.User;
import com.example.ssl.domain.UserRepository;
import com.example.ssl.web.controller.advice.exception.CUserNotFoundException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
      User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new CUserNotFoundException("해당 사용자를 찾을 수 없습니다."));
    return UserPrincipal.create(user);             
  }

  
}