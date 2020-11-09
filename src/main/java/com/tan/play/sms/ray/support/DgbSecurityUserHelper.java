package com.tan.play.sms.ray.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户支撑类
 *
 * @author 谭婧杰
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DgbSecurityUserHelper {

  /**
   * 获取当前用户
   *
   * @return Authentication
   */
  public static Authentication getCurrentUserAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  /**
   * 获取当前用户
   *
   * @return Object
   */
  public static Object getCurrentPrincipal() {
    return getCurrentUserAuthentication().getPrincipal();
  }
}
