package com.example.ssl.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  GUEST("ROLE_GUEST", "비회원"), // 여길보면 위에서 언급한것처럼 ROLE_이 앞에 붙음.
  USER("ROLE_USER", "회원"), 
  ADMIN("ROLE_ADMIN", "관리자");

  private final String key;
  private final String title;

}