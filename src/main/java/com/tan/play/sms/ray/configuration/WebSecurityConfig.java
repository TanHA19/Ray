package com.tan.play.sms.ray.configuration;

import com.tan.play.sms.ray.authentication.MyLogoutSuccessHandler;
import com.tan.play.sms.ray.authentication.RayAuthenticationFailureHandle;
import com.tan.play.sms.ray.authentication.RayAuthenticationSuccessHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/** @Author 谭婧杰 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final RayAuthenticationSuccessHandle authenticationSuccessHandle;

  private final RayAuthenticationFailureHandle rayAuthenticationFailureHandle;

  private final MyLogoutSuccessHandler myLogoutSuccessHandler;

  @Lazy
  private  UserDetailsService myUserDetailServiceImpl;
  public WebSecurityConfig(
          RayAuthenticationSuccessHandle authenticationSuccessHandle,
          RayAuthenticationFailureHandle rayAuthenticationFailureHandle,
          MyLogoutSuccessHandler myLogoutSuccessHandler) {
    this.authenticationSuccessHandle = authenticationSuccessHandle;
    this.rayAuthenticationFailureHandle = rayAuthenticationFailureHandle;
    this.myLogoutSuccessHandler = myLogoutSuccessHandler;

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin()
        .loginPage("/raySignIn")
        .loginProcessingUrl("/authentication/from")
        .successHandler(authenticationSuccessHandle)
        .failureHandler(rayAuthenticationFailureHandle)
        .and()
        .authorizeRequests()
        .antMatchers(
            "http://sms.jpsw666.com:18002/send.do",
            "/toRegister",
            "/user/register",
            "/raySignIn",
            "/assets/css/**",
            "/assets/images/**",
            "/assets/fonts/**",
            "/assets/js/**",
            "http://localhost:8082/reception/info")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .rememberMe()
        .tokenRepository(persistentTokenRepository()).userDetailsService(myUserDetailServiceImpl)
        .tokenValiditySeconds(1000)
        .and()
        .csrf()
        .disable()
        .logout()
        .logoutUrl("/rayLogout")
        .logoutSuccessHandler(myLogoutSuccessHandler)
        .deleteCookies("JSESSIONID")
        .permitAll();
  }

  @Autowired
  private DataSource dataSource;
  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
    tokenRepository.setDataSource(dataSource);
    return tokenRepository;
  }
}
