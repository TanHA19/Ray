package com.tan.play.sms.ray.exception;

/** @Author 谭婧杰 */
public interface BaseErrorInfoInterface {

  /**错误码
   * @return String
   */
  String getResultCode();

  /** 错误描述
   *
   * @return String
   */
  String getResultMsg();
}
