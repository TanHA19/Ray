package com.tan.play.sms.ray.authentication;

import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.commons.Constant;
import com.tan.play.sms.ray.service.IMailService;
import com.tan.play.sms.ray.service.RayUserInfoService;
import com.tan.play.sms.ray.support.DgbSecurityUserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/** @Author 谭婧杰 */
@Slf4j
@Component("rayAuthenticationSuccessHandle")
public class RayAuthenticationSuccessHandle implements AuthenticationSuccessHandler {

  @Value("${ray.send.login.content}")
  private String content;

  @Value("${ray.send.login.subject}")
  private String subject;

  @Value("${ray.admin.userId}")
  private String userId;

  @Value("${ray.admin.phone}")
  String adminUserPhone;

  private final IMailService iMailService;
  private final RayUserInfoService<RayUserinfo> rayUserInfoService;

  public RayAuthenticationSuccessHandle(
      IMailService iMailService, RayUserInfoService<RayUserinfo> rayUserInfoService) {
    this.iMailService = iMailService;
    this.rayUserInfoService = rayUserInfoService;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Authentication authentication)
      throws IOException {

    log.info("【商务短信平台】 欢迎登录| 登录用户名：【 " + authentication.getName() + " 】");

    log.info("【商务短信平台】 开始加载权限信息：【 " + userId + " 】");
    httpServletRequest.getSession().setAttribute("admin", userId);

    UserDetails userDetails = (UserDetails) DgbSecurityUserHelper.getCurrentPrincipal();

    List<RayUserinfo> byUserOrPwd =
        rayUserInfoService.findByUserOrPwd(userDetails.getUsername(), userDetails.getPassword());

    String redirectUrl = Constant.LOGIN_URL;

    if (!byUserOrPwd.isEmpty()) {

      log.info("【商务短信平台】 开始发送登录短信到邮件 " + byUserOrPwd.get(0).getEmail());

      CacheConfig.rayUserInfoCache = byUserOrPwd.get(0);

      iMailService.sendSimpleMail(byUserOrPwd.get(0).getEmail(), subject, content);
      httpServletRequest.getSession().setAttribute("userName", userDetails.getUsername());
      httpServletRequest.getSession().setAttribute("userId", byUserOrPwd.get(0).getId());
      httpServletRequest.getSession().setAttribute("adminUserPhone", adminUserPhone);
    }

    SavedRequest savedRequest =
        (SavedRequest)
            httpServletRequest.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
    if (savedRequest != null) {
      redirectUrl = savedRequest.getRedirectUrl();
      httpServletRequest.getSession().removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
    }

    httpServletResponse.sendRedirect(redirectUrl);
  }
}
