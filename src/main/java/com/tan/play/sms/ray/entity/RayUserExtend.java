package com.tan.play.sms.ray.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ray_sendsms.ray_user_extend")
public class RayUserExtend {
  /** 扩展信息主键 */
  @Id
  @Column(name = "extend_id")
  private Integer extendId;

  /** 客户uid */
  private String uid;

  /** 会员主键 唯一索引 */
  @Column(name = "user_id")
  private String userId;

  /** 出生日期 */
  @Column(name = "user_birthday")
  private String userBirthday;

  /** 会员级别 根据会员的总积分定义级别01注册会员,02铜牌会员,03银牌会员,04金牌会员,05钻石会员 */
  @Column(name = "user_level")
  private Integer userLevel;

  /** 地址 */
  @Column(name = "user_address")
  private String userAddress;

  /** 邮政编码 */
  @Column(name = "user_post_code")
  private String userPostCode;

  /** 联系人电话 */
  @Column(name = "user_contact_name")
  private String userContactName;

  /** 个人图像 */
  @Column(name = "user_photo_url")
  private String userPhotoUrl;

  /** 用户公司名称 */
  @Column(name = "user_company_name")
  private String userCompanyName;

  /** 用户回复同步地址 */
  @Column(name = "user_reply_address")
  private String userReplyAddress;

  /** 用户状态同步地址 */
  @Column(name = "user_statu_address")
  private String userStatuAddress;

  /** 创建用户ID 管理员用户Id */
  @Column(name = "user_userid")
  private String userUserid;

  /** 创建时间 */
  @Column(name = "user_ctime")
  private String userCtime;

  /** 更新时间 */
  @Column(name = "user_utime")
  private String userUtime;

  /**
   * 获取扩展信息主键
   *
   * @return extend_id - 扩展信息主键
   */
  public Integer getExtendId() {
    return extendId;
  }

  /**
   * 设置扩展信息主键
   *
   * @param extendId 扩展信息主键
   */
  public void setExtendId(Integer extendId) {
    this.extendId = extendId;
  }

  /**
   * 获取客户uid
   *
   * @return uid - 客户uid
   */
  public String getUid() {
    return uid;
  }

  /**
   * 设置客户uid
   *
   * @param uid 客户uid
   */
  public void setUid(String uid) {
    this.uid = uid;
  }

  /**
   * 获取会员主键 唯一索引
   *
   * @return user_id - 会员主键 唯一索引
   */
  public String getUserId() {
    return userId;
  }

  /**
   * 设置会员主键 唯一索引
   *
   * @param userId 会员主键 唯一索引
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * 获取出生日期
   *
   * @return user_birthday - 出生日期
   */
  public String getUserBirthday() {
    return userBirthday;
  }

  /**
   * 设置出生日期
   *
   * @param userBirthday 出生日期
   */
  public void setUserBirthday(String userBirthday) {
    this.userBirthday = userBirthday;
  }

  /**
   * 获取会员级别 根据会员的总积分定义级别01注册会员,02铜牌会员,03银牌会员,04金牌会员,05钻石会员
   *
   * @return user_level - 会员级别 根据会员的总积分定义级别01注册会员,02铜牌会员,03银牌会员,04金牌会员,05钻石会员
   */
  public Integer getUserLevel() {
    return userLevel;
  }

  /**
   * 设置会员级别 根据会员的总积分定义级别01注册会员,02铜牌会员,03银牌会员,04金牌会员,05钻石会员
   *
   * @param userLevel 会员级别 根据会员的总积分定义级别01注册会员,02铜牌会员,03银牌会员,04金牌会员,05钻石会员
   */
  public void setUserLevel(Integer userLevel) {
    this.userLevel = userLevel;
  }

  /**
   * 获取地址
   *
   * @return user_address - 地址
   */
  public String getUserAddress() {
    return userAddress;
  }

  /**
   * 设置地址
   *
   * @param userAddress 地址
   */
  public void setUserAddress(String userAddress) {
    this.userAddress = userAddress;
  }

  /**
   * 获取邮政编码
   *
   * @return user_post_code - 邮政编码
   */
  public String getUserPostCode() {
    return userPostCode;
  }

  /**
   * 设置邮政编码
   *
   * @param userPostCode 邮政编码
   */
  public void setUserPostCode(String userPostCode) {
    this.userPostCode = userPostCode;
  }

  /**
   * 获取联系人电话
   *
   * @return user_contact_name - 联系人电话
   */
  public String getUserContactName() {
    return userContactName;
  }

  /**
   * 设置联系人电话
   *
   * @param userContactName 联系人电话
   */
  public void setUserContactName(String userContactName) {
    this.userContactName = userContactName;
  }

  /**
   * 获取个人图像
   *
   * @return user_photo_url - 个人图像
   */
  public String getUserPhotoUrl() {
    return userPhotoUrl;
  }

  /**
   * 设置个人图像
   *
   * @param userPhotoUrl 个人图像
   */
  public void setUserPhotoUrl(String userPhotoUrl) {
    this.userPhotoUrl = userPhotoUrl;
  }

  /**
   * 获取用户公司名称
   *
   * @return user_company_name - 用户公司名称
   */
  public String getUserCompanyName() {
    return userCompanyName;
  }

  /**
   * 设置用户公司名称
   *
   * @param userCompanyName 用户公司名称
   */
  public void setUserCompanyName(String userCompanyName) {
    this.userCompanyName = userCompanyName;
  }

  /**
   * 获取用户回复同步地址
   *
   * @return user_reply_address - 用户回复同步地址
   */
  public String getUserReplyAddress() {
    return userReplyAddress;
  }

  /**
   * 设置用户回复同步地址
   *
   * @param userReplyAddress 用户回复同步地址
   */
  public void setUserReplyAddress(String userReplyAddress) {
    this.userReplyAddress = userReplyAddress;
  }

  /**
   * 获取用户状态同步地址
   *
   * @return user_statu_address - 用户状态同步地址
   */
  public String getUserStatuAddress() {
    return userStatuAddress;
  }

  /**
   * 设置用户状态同步地址
   *
   * @param userStatuAddress 用户状态同步地址
   */
  public void setUserStatuAddress(String userStatuAddress) {
    this.userStatuAddress = userStatuAddress;
  }

  /**
   * 获取创建用户ID 管理员用户Id
   *
   * @return user_userid - 创建用户ID 管理员用户Id
   */
  public String getUserUserid() {
    return userUserid;
  }

  /**
   * 设置创建用户ID 管理员用户Id
   *
   * @param userUserid 创建用户ID 管理员用户Id
   */
  public void setUserUserid(String userUserid) {
    this.userUserid = userUserid;
  }

  /**
   * 获取创建时间
   *
   * @return user_ctime - 创建时间
   */
  public String getUserCtime() {
    return userCtime;
  }

  /**
   * 设置创建时间
   *
   * @param userCtime 创建时间
   */
  public void setUserCtime(String userCtime) {
    this.userCtime = userCtime;
  }

  /**
   * 获取更新时间
   *
   * @return user_utime - 更新时间
   */
  public String getUserUtime() {
    return userUtime;
  }

  /**
   * 设置更新时间
   *
   * @param userUtime 更新时间
   */
  public void setUserUtime(String userUtime) {
    this.userUtime = userUtime;
  }
}
