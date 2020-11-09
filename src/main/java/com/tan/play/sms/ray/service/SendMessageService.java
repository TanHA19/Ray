package com.tan.play.sms.ray.service;

import com.tan.play.sms.ray.entity.vo.SendMessageRequest;

import java.io.IOException;

/**
 * @author 谭婧杰
 */
public interface SendMessageService{
  /**
   * 发送信息
   * @param sendMessageRequest 发送信息请求类
   * @return String
   * @throws IOException IO异常
   */
  String sendMessage(SendMessageRequest sendMessageRequest) throws IOException;
}
