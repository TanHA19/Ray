package com.tan.play.sms.ray.entity.commons;

import com.tan.play.sms.ray.entity.commons.emun.ResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回结果 @Author tan
 *
 * @date 2020/9/13 1:10
 */
@Getter
@Setter
@ToString
public class JsonResult<T> implements Serializable {
  private Boolean success;
  private Integer errorCode;
  private String errorMsg;
  private T data;

  public JsonResult() {}

  public JsonResult(boolean success) {
    this.success = success;
    this.errorCode = success ? ResultCode.SUCCESS.getCode() : ResultCode.COMMON_FAIL.getCode();
    this.errorMsg = success ? ResultCode.SUCCESS.getMessage() : ResultCode.COMMON_FAIL.getMessage();
  }

  public JsonResult(boolean success, ResultCode resultEnum) {
    this.success = success;
    this.errorCode =
        success
            ? ResultCode.SUCCESS.getCode()
            : (resultEnum == null ? ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
    this.errorMsg =
        success
            ? ResultCode.SUCCESS.getMessage()
            : (resultEnum == null ? ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
  }

  public JsonResult(boolean success, T data) {
    this.success = success;
    this.errorCode = success ? ResultCode.SUCCESS.getCode() : ResultCode.COMMON_FAIL.getCode();
    this.errorMsg = success ? ResultCode.SUCCESS.getMessage() : ResultCode.COMMON_FAIL.getMessage();
    this.data = data;
  }

  public JsonResult(boolean success, ResultCode resultEnum, T data) {
    this.success = success;
    this.errorCode =
        success
            ? ResultCode.SUCCESS.getCode()
            : (resultEnum == null ? ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
    this.errorMsg =
        success
            ? ResultCode.SUCCESS.getMessage()
            : (resultEnum == null ? ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
    this.data = data;
  }
}
