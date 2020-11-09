package com.tan.play.sms.ray.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ray_sendsms.ray_sys_announcement")
public class RaySysAnnouncement {
  /** ID */
  @Id
  @Column(name = "announcement_id")
  private String announcementId;

  /** 公告标题 */
  @Column(name = "announcement_title")
  private String announcementTitle;

  /** 公告创建人 */
  @Column(name = "announcement_userId")
  private String announcementUserid;

  /** 公告类型 1普通公告 */
  @Column(name = "announcement_type")
  private String announcementType;

  /** 发布时间 */
  @Column(name = "announcement_publish_time")
  private String announcementPublishTime;

  /** 状态 0 正常 1 删除 */
  @Column(name = "announcement_state")
  private String announcementState;

  /** 创建时间 */
  @Column(name = "announcement_create_time")
  private String announcementCreateTime;

  /** 修改时间 */
  @Column(name = "announcement_update_time")
  private String announcementUpdateTime;

  /** 修改人 */
  @Column(name = "announcement_update_user")
  private String announcementUpdateUser;

  /** 公告内容 */
  @Column(name = "announcement_content")
  private String announcementContent;

  /**
   * 获取ID
   *
   * @return announcement_id - ID
   */
  public String getAnnouncementId() {
    return announcementId;
  }

  /**
   * 设置ID
   *
   * @param announcementId ID
   */
  public void setAnnouncementId(String announcementId) {
    this.announcementId = announcementId;
  }

  /**
   * 获取公告标题
   *
   * @return announcement_title - 公告标题
   */
  public String getAnnouncementTitle() {
    return announcementTitle;
  }

  /**
   * 设置公告标题
   *
   * @param announcementTitle 公告标题
   */
  public void setAnnouncementTitle(String announcementTitle) {
    this.announcementTitle = announcementTitle;
  }

  /**
   * 获取公告创建人
   *
   * @return announcement_userId - 公告创建人
   */
  public String getAnnouncementUserid() {
    return announcementUserid;
  }

  /**
   * 设置公告创建人
   *
   * @param announcementUserid 公告创建人
   */
  public void setAnnouncementUserid(String announcementUserid) {
    this.announcementUserid = announcementUserid;
  }

  /**
   * 获取公告类型 1普通公告
   *
   * @return announcement_type - 公告类型 1普通公告
   */
  public String getAnnouncementType() {
    return announcementType;
  }

  /**
   * 设置公告类型 1普通公告
   *
   * @param announcementType 公告类型 1普通公告
   */
  public void setAnnouncementType(String announcementType) {
    this.announcementType = announcementType;
  }

  /**
   * 获取发布时间
   *
   * @return announcement_publish_time - 发布时间
   */
  public String getAnnouncementPublishTime() {
    return announcementPublishTime;
  }

  /**
   * 设置发布时间
   *
   * @param announcementPublishTime 发布时间
   */
  public void setAnnouncementPublishTime(String announcementPublishTime) {
    this.announcementPublishTime = announcementPublishTime;
  }

  /**
   * 获取状态 0 正常 1 删除
   *
   * @return announcement_state - 状态 0 正常 1 删除
   */
  public String getAnnouncementState() {
    return announcementState;
  }

  /**
   * 设置状态 0 正常 1 删除
   *
   * @param announcementState 状态 0 正常 1 删除
   */
  public void setAnnouncementState(String announcementState) {
    this.announcementState = announcementState;
  }

  /**
   * 获取创建时间
   *
   * @return announcement_create_time - 创建时间
   */
  public String getAnnouncementCreateTime() {
    return announcementCreateTime;
  }

  /**
   * 设置创建时间
   *
   * @param announcementCreateTime 创建时间
   */
  public void setAnnouncementCreateTime(String announcementCreateTime) {
    this.announcementCreateTime = announcementCreateTime;
  }

  /**
   * 获取修改时间
   *
   * @return announcement_update_time - 修改时间
   */
  public String getAnnouncementUpdateTime() {
    return announcementUpdateTime;
  }

  /**
   * 设置修改时间
   *
   * @param announcementUpdateTime 修改时间
   */
  public void setAnnouncementUpdateTime(String announcementUpdateTime) {
    this.announcementUpdateTime = announcementUpdateTime;
  }

  /**
   * 获取修改人
   *
   * @return announcement_update_user - 修改人
   */
  public String getAnnouncementUpdateUser() {
    return announcementUpdateUser;
  }

  /**
   * 设置修改人
   *
   * @param announcementUpdateUser 修改人
   */
  public void setAnnouncementUpdateUser(String announcementUpdateUser) {
    this.announcementUpdateUser = announcementUpdateUser;
  }

  /**
   * 获取公告内容
   *
   * @return announcement_content - 公告内容
   */
  public String getAnnouncementContent() {
    return announcementContent;
  }

  /**
   * 设置公告内容
   *
   * @param announcementContent 公告内容
   */
  public void setAnnouncementContent(String announcementContent) {
    this.announcementContent = announcementContent;
  }
}
