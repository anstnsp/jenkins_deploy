package com.example.ssl.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Spring Boot를 사용하시는 경우에는 위의 예제처럼 @SpringBootTest을 통하여 설정을 로드하게 되는데 
 * @SpringBootTest 어노테이션에는 @ExtendWith(SpringExtension.class) 가 포함되어 있으므로 JUnit5
 * 로 변경시에는 @RunWith(SpringRunner.class)를 삭제하시면 됩니다.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest //@DataJpaTest : JPA테스트를 위한 어노테이션 
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository; 
  private BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

  @Test
  public void 저장하고_유저확인() {
    //given
    String email = "anstnsp@naver.com";
    String password = passwordEncoder.encode("123");
    String name = "문수네";
    Role role = Role.USER;
    User user = User.builder().email(email).password(password).name(name).role(role).build();
    userRepository.save(user); 

    //when 
    User findedUser = userRepository.findByEmail(email).orElseGet(null);
    //then 
    assertNotNull(findedUser);
    assertEquals(name, findedUser.getName());
    assertEquals(email, findedUser.getEmail());
  }
}