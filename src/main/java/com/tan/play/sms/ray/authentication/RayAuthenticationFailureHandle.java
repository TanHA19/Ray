package com.tan.play.sms.ray.authentication;

import com.alibaba.fastjson.JSONObject;
import com.tan.play.sms.ray.entity.commons.Constant;
import com.tan.play.sms.ray.exception.CommonEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/** @Author 谭婧杰 */
@Slf4j
@Component("rayAuthenticationFailureHandle")
public class RayAuthenticationFailureHandle implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AuthenticationException e)
      throws IOException {
    log.info("【商务短信平台】 登录失败");

    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.setContentType(Constant.ENCODING_TYPE);
    JSONObject res = new JSONObject();
    res.put("status", CommonEnum.FAIL);
    res.put("msg", e.getMessage());
    PrintWriter out = httpServletResponse.getWriter();
    out.write(res.toString());
    out.flush();
    out.close();
  }
}
