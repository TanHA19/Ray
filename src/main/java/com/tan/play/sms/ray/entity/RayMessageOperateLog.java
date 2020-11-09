package com.tan.play.sms.ray.entity;

import javax.persistence.*;

@Table(name = "ray_sendsms.ray_message_operate_log")
public class RayMessageOperateLog {
  /** 主键id */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  /** 平台用户ID */
  @Column(name = "user_id")
  private String userId;

  /** 短信类型 01 普通短信 02 大容量短信 03 个性短信 */
  @Column(name = "message_type")
  private String messageType;

  /** 请求返回批次号 */
  @Column(name = "message_batch")
  private String messageBatch;

  /** 请求返回失败原因描述 */
  @Column(name = "message_fail_reason")
  private String messageFailReason;

  /** 0:发送成功；1:发送失败 */
  @Column(name = "result_flag")
  private String resultFlag;

  /** 失败原因编码 */
  @Column(name = "fail_reason_code")
  private String failReasonCode;

  /** 失败原因描述 */
  @Column(name = "fail_reason")
  private String failReason;

  @Column(name = "create_time")
  private String createTime;

  /** 更新时间 */
  @Column(name = "update_time")
  private String updateTime;

  /** 数据有效标识(0：删除，1：有效) */
  private Integer status;

  /** 请求发送参数手机号 */
  @Column(name = "message_phone_content")
  private String messagePhoneContent;

  /** 请求发送内容 */
  @Column(name = "message_content")
  private String messageContent;

  /**
   * 获取主键id
   *
   * @return id - 主键id
   */
  public String getId() {
    return id;
  }

  @Transient private String userName;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * 设置主键id
   *
   * @param id 主键id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 获取平台用户ID
   *
   * @return user_id - 平台用户ID
   */
  public String getUserId() {
    return userId;
  }

  /**
   * 设置平台用户ID
   *
   * @param userId 平台用户ID
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * 获取短信类型 01 普通短信 02 大容量短信 03 个性短信
   *
   * @return message_type - 短信类型 01 普通短信 02 大容量短信 03 个性短信
   */
  public String getMessageType() {
    return messageType;
  }

  /**
   * 设置短信类型 01 普通短信 02 大容量短信 03 个性短信
   *
   * @param messageType 短信类型 01 普通短信 02 大容量短信 03 个性短信
   */
  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  /**
   * 获取请求返回批次号
   *
   * @return message_batch - 请求返回批次号
   */
  public String getMessageBatch() {
    return messageBatch;
  }

  /**
   * 设置请求返回批次号
   *
   * @param messageBatch 请求返回批次号
   */
  public void setMessageBatch(String messageBatch) {
    this.messageBatch = messageBatch;
  }

  /**
   * 获取请求返回失败原因描述
   *
   * @return message_fail_reason - 请求返回失败原因描述
   */
  public String getMessageFailReason() {
    return messageFailReason;
  }

  /**
   * 设置请求返回失败原因描述
   *
   * @param messageFailReason 请求返回失败原因描述
   */
  public void setMessageFailReason(String messageFailReason) {
    this.messageFailReason = messageFailReason;
  }

  /**
   * 获取0:发送成功；1:发送失败
   *
   * @return result_flag - 0:发送成功；1:发送失败
   */
  public String getResultFlag() {
    return resultFlag;
  }

  /**
   * 设置0:发送成功；1:发送失败
   *
   * @param resultFlag 0:发送成功；1:发送失败
   */
  public void setResultFlag(String resultFlag) {
    this.resultFlag = resultFlag;
  }

  /**
   * 获取失败原因编码
   *
   * @return fail_reason_code - 失败原因编码
   */
  public String getFailReasonCode() {
    return failReasonCode;
  }

  /**
   * 设置失败原因编码
   *
   * @param failReasonCode 失败原因编码
   */
  public void setFailReasonCode(String failReasonCode) {
    this.failReasonCode = failReasonCode;
  }

  /**
   * 获取失败原因描述
   *
   * @return fail_reason - 失败原因描述
   */
  public String getFailReason() {
    return failReason;
  }

  /**
   * 设置失败原因描述
   *
   * @param failReason 失败原因描述
   */
  public void setFailReason(String failReason) {
    this.failReason = failReason;
  }

  /** @return create_time */
  public String getCreateTime() {
    return createTime;
  }

  /** @param createTime */
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  /**
   * 获取更新时间
   *
   * @return update_time - 更新时间
   */
  public String getUpdateTime() {
    return updateTime;
  }

  /**
   * 设置更新时间
   *
   * @param updateTime 更新时间
   */
  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  /**
   * 获取数据有效标识(0：删除，1：有效)
   *
   * @return status - 数据有效标识(0：删除，1：有效)
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * 设置数据有效标识(0：删除，1：有效)
   *
   * @param status 数据有效标识(0：删除，1：有效)
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * 获取请求发送参数手机号
   *
   * @return message_phone_content - 请求发送参数手机号
   */
  public String getMessagePhoneContent() {
    return messagePhoneContent;
  }

  /**
   * 设置请求发送参数手机号
   *
   * @param messagePhoneContent 请求发送参数手机号
   */
  public void setMessagePhoneContent(String messagePhoneContent) {
    this.messagePhoneContent = messagePhoneContent;
  }

  /**
   * 获取请求发送内容
   *
   * @return message_content - 请求发送内容
   */
  public String getMessageContent() {
    return messageContent;
  }

  /**
   * 设置请求发送内容
   *
   * @param messageContent 请求发送内容
   */
  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }
}
