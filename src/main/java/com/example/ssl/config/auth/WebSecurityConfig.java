package com.example.ssl.config.auth;

import lombok.RequiredArgsConstructor;

import com.example.ssl.security.CustomUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 리소스에 대한 접근권한 설정은 맨 아래와 같이 configure메소드 내부에서 authorizeRequests()를 통해 셋팅 할수도 있고 
 * @EnableGlobalMethodSecurity 를 통해서 각 컨트롤러에 접근권한을 셋팅 해줄 수 있다. 
 * configure()에 셋팅하는 장점은 리소스권한을 중앙관리 할 수 있다는 장점이 있음. 
 */

@RequiredArgsConstructor
@EnableWebSecurity  //@EnableWebSecurity => Spring Security 설정들을 활성화 시켜줌. 
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;

  //암호화에 필요한 PasswordEncoder 를 Bean으로 등록 . 
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(jwtTokenProvider);
  }
  
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
  }
  

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().headers().frameOptions().disable().and()
            .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
            .csrf().disable() // csrf 보안 토큰 disable처리.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
            .and()
            .formLogin().disable()
            .authorizeRequests() // 요청에 대한 사용권한 체크 시작점.
              .antMatchers("/*/admin/**").hasRole("ADMIN") //ADMIN 쓰면 앞에 ROLE_가 자동으로 삽입댐 ROLE_ADMIN
              .antMatchers("/*/users/**").hasRole("USER")
              .antMatchers("/*/signin", "/*/signin/**", "/*/signup", "/*/signup/**", "/social/**").permitAll()  //회원가입 및 로그인은 아무나 가능.
              .antMatchers(HttpMethod.GET, "/exception/**", "/actuator/health","/v1/board/**", "favicon.ico").permitAll()
              .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
            .and()
              .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) //JWT 검증 에러 처리위해(토큰없거나, 토큰이잘못됐거나, 유효시간지났거나)
            .and()
              .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()) //JWT 검증 에러 처리위해(해당토큰이 정상이지만 특정리소스에대한 권한이없을때)
            .and()
            // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
            // 클라이언트가 리소스를 요청할 때 접근권한이 없는 경우 기본적으로 로그인폼으로 보내는데 그 역할을 하는 필터가 UserNamePasswordAuthenticationFilter다.
            // 따라서 로그인폼으로 보내기 전(username...필터 전) JWT토큰 검증을 해 검증이 실패하면 JSON으로 인증오류를 뱉기위해 해당 필터를 아래 한줄이 추가됨. 
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                    UsernamePasswordAuthenticationFilter.class);   
           
    }

    //ignore check swagger resource 
    // @Override
    // public void configure(WebSecurity web) {
    //   web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", 
    //     "/swagger-ui.html", "/webjars/**", "/swagger/**") 
    // }
}