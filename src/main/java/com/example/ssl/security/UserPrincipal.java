package com.example.ssl.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.example.ssl.domain.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

  /**
   *
   */
  private static final long serialVersionUID = -4332448941136498975L;

  private Long id; 
  private String email; 
  private String password; 
  private Collection<? extends GrantedAuthority> authorities; 

  public static UserPrincipal create(User user) {
    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), authorities);
  } 

  // public static UserPrincipal create(User user, Map<String, Object> attributes) {
  //     UserPrincipal userPrincipal = UserPrincipal.create(user);
  //     userPrincipal.setAttributes(attributes);
  //     return userPrincipal;
  // }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getPassword() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getUsername() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isEnabled() {
    // TODO Auto-generated method stub
    return false;
  }

}