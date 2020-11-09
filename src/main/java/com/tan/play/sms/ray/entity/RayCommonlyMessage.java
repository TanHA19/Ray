package com.tan.play.sms.ray.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Table(name = "ray_sendsms.ray_commonly_message")
public class RayCommonlyMessage {
  @Id
  @Column(name = "message_id")
  private String messageId;

  /** 短信内容所属Id */
  @Column(name = "user_message_id")
  private String userMessageId;

  /** 短信内容 */
  @NotNull(message = "短信内容不能为空")
  @Column(name = "message_push_content")
  private String messagePushContent;

  /** 短信内容状态 0：正常 1：删除 */
  @Column(name = "message_state")
  private String messageState;

  /** 创建时间 */
  @Column(name = "message_createtime")
  private String messageCreatetime;

  /** 更新时间 */
  @Column(name = "message_updatetime")
  private String messageUpdatetime;

  /** 短信所属人姓名 前端展示字段 */
  @Transient private String userMessageName;

  /** @return message_id */
  public String getMessageId() {
    return messageId;
  }

  /** @param messageId */
  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  /**
   * 获取短信内容所属Id
   *
   * @return user_message_id - 短信内容所属Id
   */
  public String getUserMessageId() {
    return userMessageId;
  }

  /**
   * 设置短信内容所属Id
   *
   * @param userMessageId 短信内容所属Id
   */
  public void setUserMessageId(String userMessageId) {
    this.userMessageId = userMessageId;
  }

  /**
   * 获取短信内容
   *
   * @return message_push_content - 短信内容
   */
  public String getMessagePushContent() {
    return messagePushContent;
  }

  /**
   * 设置短信内容
   *
   * @param messagePushContent 短信内容
   */
  public void setMessagePushContent(String messagePushContent) {
    this.messagePushContent = messagePushContent;
  }

  /**
   * 获取短信内容状态 0：正常 1：删除
   *
   * @return message_state - 短信内容状态 0：正常 1：删除
   */
  public String getMessageState() {
    return messageState;
  }

  /**
   * 设置短信内容状态 0：正常 1：删除
   *
   * @param messageState 短信内容状态 0：正常 1：删除
   */
  public void setMessageState(String messageState) {
    this.messageState = messageState;
  }

  /**
   * 获取创建时间
   *
   * @return message_createtime - 创建时间
   */
  public String getMessageCreatetime() {
    return messageCreatetime;
  }

  /**
   * 设置创建时间
   *
   * @param messageCreatetime 创建时间
   */
  public void setMessageCreatetime(String messageCreatetime) {
    this.messageCreatetime = messageCreatetime;
  }

  /**
   * 获取更新时间
   *
   * @return message_updatetime - 更新时间
   */
  public String getMessageUpdatetime() {
    return messageUpdatetime;
  }

  /**
   * 设置更新时间
   *
   * @param messageUpdatetime 更新时间
   */
  public void setMessageUpdatetime(String messageUpdatetime) {
    this.messageUpdatetime = messageUpdatetime;
  }

  public String getUserMessageName() {
    return userMessageName;
  }

  public void setUserMessageName(String userMessageName) {
    this.userMessageName = userMessageName;
  }
}
