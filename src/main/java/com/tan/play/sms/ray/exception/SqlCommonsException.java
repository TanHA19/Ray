package com.tan.play.sms.ray.exception;

import lombok.Data;

/** @Author 谭婧杰 */
@Data
public class SqlCommonsException extends RuntimeException {

  /** 错误码 */
  protected String errorCode;
  /** 错误信息 */
  protected String errorMsg;


  @Override
  public String getMessage() {
    return errorMsg;
  }

  @Override
  public Throwable fillInStackTrace() {
    return this;
  }
}
