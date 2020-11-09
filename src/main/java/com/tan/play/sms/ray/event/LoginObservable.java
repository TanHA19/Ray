package com.tan.play.sms.ray.event;

import org.springframework.context.ApplicationEvent;

/** @Author 谭婧杰 */
public class LoginObservable extends ApplicationEvent {

  public LoginObservable(Object source) {

    super(source);
  }

  @Override
  public Object getSource() {
    return super.getSource();
  }
}
