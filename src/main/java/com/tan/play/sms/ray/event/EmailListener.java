package com.tan.play.sms.ray.event;

import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/** @Author 谭婧杰 */
@Slf4j
@Component
public class EmailListener implements ApplicationListener<LoginObservable> {

  private final IMailService iMailService;

  public EmailListener(IMailService iMailService) {
    this.iMailService = iMailService;
  }

  @Value("ray.send.login.content")
  private String content;

  @Value("  ray.send.login.subject")
  private String subject;

  @Override
  @Async
  public void onApplicationEvent(LoginObservable loginObservable) {
    log.info("【商务短信平台】" + Thread.currentThread() + "用户登录成功！发送超过信息邮件");

    RayUserinfo rayUserInfo = (RayUserinfo) loginObservable.getSource();
    iMailService.sendSimpleMail(rayUserInfo.getEmail(), subject, content);
  }
}
