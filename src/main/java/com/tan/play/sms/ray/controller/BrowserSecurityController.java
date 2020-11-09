package com.tan.play.sms.ray.controller;

import com.tan.play.sms.ray.entity.commons.Constant;
import com.tan.play.sms.ray.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @Author 谭婧杰 */
@Slf4j
@RestController
public class BrowserSecurityController {

  private RequestCache requestCache = new HttpSessionRequestCache();

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @GetMapping("/authentication/require")
  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  public SimpleResponse requireAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws IOException {

    SavedRequest savedRequest = requestCache.getRequest(request, response);

    if (savedRequest != null) {
      java.lang.String redirectUrl = savedRequest.getRedirectUrl();
      log.info("【商务短信平台】 ： 用户引发跳转的请求是  " + redirectUrl);
      redirectStrategy.sendRedirect(request, response, Constant.LOGIN_URL1);
    }
    return new SimpleResponse("服务的服务需要登录，请先进行登录授权");
  }
}
