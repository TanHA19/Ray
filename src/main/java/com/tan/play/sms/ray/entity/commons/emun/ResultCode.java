package com.tan.play.sms.ray.entity.commons.emun;

/**
 * @author 谭婧杰
 * @date 2020/9/13 1:16
 */
public enum ResultCode {
  /* 成功 */
  SUCCESS(200, "成功"),

  /* 默认失败 */
  COMMON_FAIL(999, "失败"),
  /* 用户 */
  USER_CREDENTIALS_EXPIRED(2001, "密码不一致"),
  USER_ACCOUNT_NOT_EXIST(2002, "账号不存在"),

  /* 业务 */
  NO_PERMISSION(3001, "没有权限,请联系客户，进行充值");

  private Integer code;
  private String message;

  ResultCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
