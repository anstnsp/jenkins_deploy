package com.example.ssl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.ssl.web.dto.request.user.UserInfoModiDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; 

  @Column(length = 100, nullable = false, unique = true)
  private String email;

  @Column(length = 300, nullable = false)
  private String password;
  
  @Column(length = 20, nullable = true)
  private String name; 

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role; 

  public void update(UserInfoModiDTO userInfoModiDTO) {
    this.name = userInfoModiDTO.getName();
  }


}