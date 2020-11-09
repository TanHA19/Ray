package com.tan.play.sms.ray.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/** @Author tan */
@Data
@AllArgsConstructor
public class SendMessageRequest {

  public String phoneNumStr;
  public String phoneContent;
  public String sendWay;
  public String sendTimeTask;
  /**
   * 短信类型 01 普通短信 02 大容量短信 03 个性短信
   */
  public String messageType;
}
