package com.tan.play.sms.ray.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** @Author tan */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UserNotFindException extends RuntimeException {

  private static final long serialVersionUID = 1L;

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
