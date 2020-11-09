package com.tan.play.sms.ray.exception;

/** @Author 谭婧杰 */
public enum CommonEnum implements BaseErrorInfoInterface {
  // 数据操作错误定义
  SUCCESS("200", "提交成功!"),
  FAIL("201", "提交失败!"),
  NOT_FOUND("404", "未找到该资源!"),
  USER_NOT_FOUND("404", "用户未找到！"),
  PWD_NOT_EMPTY("-1", "uid或者密码为空！"),
  PHONE_NOT_EMPTY("-2", "下发号码为空！"),
  CONTEXT_NOT_EMPTY("-3", "下发内容为空！"),
  CONTEXT_NOT_LENGTH("-4", "下发内容过长！"),
  PHONE_NOT_LENGTH("-5", "下发号码过长！"),
  PWD_NOT_SUCCESS("-6", "uid或者密码不正确"),
  USER_NOT_SUFFICIENT("-7", "余额不足"),
  UID_NOT_AUTHORIZATION("-8", "uid授权未通过"),
  PROTOCOL_NOT_AUTHORIZATION("-9", "协议类不正确"),
  SYS_FAIL("-99", "系统异常"),
  /* 发送状态 */
  SEND_SUCCESS("0", "发送成功"),
  SEND_FAIL("1", "发送失败");
  ;

  /** 错误码 */
  private String resultCode;

  /** 错误描述 */
  private String resultMsg;

  CommonEnum(String resultCode, String resultMsg) {
    this.resultCode = resultCode;
    this.resultMsg = resultMsg;
  }

  @Override
  public String getResultCode() {
    return resultCode;
  }

  @Override
  public String getResultMsg() {
    return resultMsg;
  }
}
