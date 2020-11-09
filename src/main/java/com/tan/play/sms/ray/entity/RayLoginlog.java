package com.tan.play.sms.ray.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ray_sendsms.ray_loginlog")
@Data
public class RayLoginlog {
  /** ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  /** 会员ID */
  @Column(name = "user_id")
  private String userId;

  /** 登陆方式 0-密码登录 */
  @Column(name = "login_type")
  private String loginType;

  /** 登录密码 */
  @Column(name = "login_pwd")
  private String loginPwd;

  /** 资金密码 */
  @Column(name = "fund_pwd")
  private String fundPwd;

  /** 手机号码(全局唯一) */
  private String phone;

  /** 电子邮箱(预留) */
  private String email;

  /** QQ号码(预留) */
  @Column(name = "QQ")
  private String qq;

  /** 微信号 用于微信登陆 */
  @Column(name = "WEIXIN")
  private String weixin;

  /** 用户SESSION UUID */
  @Column(name = "user_session")
  private String userSession;

  /** SESSION有效时间 */
  @Column(name = "session_vaild_dt")
  private String sessionVaildDt;

  /** 登录总次数 */
  @Column(name = "login_tatal_ct")
  private String loginTatalCt;

  /** 登录状态 数据字典：登录状态 */
  @Column(name = "login_result")
  private String loginResult;

  /** 最后登录时间 */
  @Column(name = "last_datetime")
  private Date lastDatetime;

  /** 登录IP */
  @Column(name = "login_ip")
  private String loginIp;

  /** 登录经度 */
  @Column(name = "LNG")
  private Integer lng;

  /** 登录维度 */
  @Column(name = "LAT")
  private Integer lat;

  /** 0:ANDROID 1:IOS 2:微信 3：WP */
  @Column(name = "push_equip_type")
  private String pushEquipType;

  /** 认证项目 1-注册 2-密码设定 */
  @Column(name = "active_item")
  private String activeItem;

  /**
   * 获取ID
   *
   * @return id - ID
   */
  public String getId() {
    return id;
  }

  /**
   * 设置ID
   *
   * @param id ID
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 获取会员ID
   *
   * @return user_id - 会员ID
   */
  public String getUserId() {
    return userId;
  }

  /**
   * 设置会员ID
   *
   * @param userId 会员ID
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * 获取登陆方式 0-密码登录
   *
   * @return login_type - 登陆方式 0-密码登录
   */
  public String getLoginType() {
    return loginType;
  }

  /**
   * 设置登陆方式 0-密码登录
   *
   * @param loginType 登陆方式 0-密码登录
   */
  public void setLoginType(String loginType) {
    this.loginType = loginType;
  }

  /**
   * 获取登录密码
   *
   * @return login_pwd - 登录密码
   */
  public String getLoginPwd() {
    return loginPwd;
  }

  /**
   * 设置登录密码
   *
   * @param loginPwd 登录密码
   */
  public void setLoginPwd(String loginPwd) {
    this.loginPwd = loginPwd;
  }

  /**
   * 获取资金密码
   *
   * @return fund_pwd - 资金密码
   */
  public String getFundPwd() {
    return fundPwd;
  }

  /**
   * 设置资金密码
   *
   * @param fundPwd 资金密码
   */
  public void setFundPwd(String fundPwd) {
    this.fundPwd = fundPwd;
  }

  /**
   * 获取手机号码(全局唯一)
   *
   * @return phone - 手机号码(全局唯一)
   */
  public String getPhone() {
    return phone;
  }

  /**
   * 设置手机号码(全局唯一)
   *
   * @param phone 手机号码(全局唯一)
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * 获取电子邮箱(预留)
   *
   * @return email - 电子邮箱(预留)
   */
  public String getEmail() {
    return email;
  }

  /**
   * 设置电子邮箱(预留)
   *
   * @param email 电子邮箱(预留)
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * 获取QQ号码(预留)
   *
   * @return QQ - QQ号码(预留)
   */
  public String getQq() {
    return qq;
  }

  /**
   * 设置QQ号码(预留)
   *
   * @param qq QQ号码(预留)
   */
  public void setQq(String qq) {
    this.qq = qq;
  }

  /**
   * 获取微信号 用于微信登陆
   *
   * @return WEIXIN - 微信号 用于微信登陆
   */
  public String getWeixin() {
    return weixin;
  }

  /**
   * 设置微信号 用于微信登陆
   *
   * @param weixin 微信号 用于微信登陆
   */
  public void setWeixin(String weixin) {
    this.weixin = weixin;
  }

  /**
   * 获取用户SESSION UUID
   *
   * @return user_session - 用户SESSION UUID
   */
  public String getUserSession() {
    return userSession;
  }

  /**
   * 设置用户SESSION UUID
   *
   * @param userSession 用户SESSION UUID
   */
  public void setUserSession(String userSession) {
    this.userSession = userSession;
  }

  /**
   * 获取SESSION有效时间
   *
   * @return session_vaild_dt - SESSION有效时间
   */
  public String getSessionVaildDt() {
    return sessionVaildDt;
  }

  /**
   * 设置SESSION有效时间
   *
   * @param sessionVaildDt SESSION有效时间
   */
  public void setSessionVaildDt(String sessionVaildDt) {
    this.sessionVaildDt = sessionVaildDt;
  }

  /**
   * 获取登录总次数
   *
   * @return login_tatal_ct - 登录总次数
   */
  public String getLoginTatalCt() {
    return loginTatalCt;
  }

  /**
   * 设置登录总次数
   *
   * @param loginTatalCt 登录总次数
   */
  public void setLoginTatalCt(String loginTatalCt) {
    this.loginTatalCt = loginTatalCt;
  }

  /**
   * 获取登录状态 数据字典：登录状态
   *
   * @return login_result - 登录状态 数据字典：登录状态
   */
  public String getLoginResult() {
    return loginResult;
  }

  /**
   * 设置登录状态 数据字典：登录状态
   *
   * @param loginResult 登录状态 数据字典：登录状态
   */
  public void setLoginResult(String loginResult) {
    this.loginResult = loginResult;
  }

  /**
   * 获取最后登录时间
   *
   * @return last_datetime - 最后登录时间
   */
  public Date getLastDatetime() {
    return lastDatetime;
  }

  /**
   * 设置最后登录时间
   *
   * @param lastDatetime 最后登录时间
   */
  public void setLastDatetime(Date lastDatetime) {
    this.lastDatetime = lastDatetime;
  }

  /**
   * 获取登录IP
   *
   * @return login_ip - 登录IP
   */
  public String getLoginIp() {
    return loginIp;
  }

  /**
   * 设置登录IP
   *
   * @param loginIp 登录IP
   */
  public void setLoginIp(String loginIp) {
    this.loginIp = loginIp;
  }

  /**
   * 获取登录经度
   *
   * @return LNG - 登录经度
   */
  public Integer getLng() {
    return lng;
  }

  /**
   * 设置登录经度
   *
   * @param lng 登录经度
   */
  public void setLng(Integer lng) {
    this.lng = lng;
  }

  /**
   * 获取登录维度
   *
   * @return LAT - 登录维度
   */
  public Integer getLat() {
    return lat;
  }

  /**
   * 设置登录维度
   *
   * @param lat 登录维度
   */
  public void setLat(Integer lat) {
    this.lat = lat;
  }

  /**
   * 获取0:ANDROID 1:IOS 2:微信 3：WP
   *
   * @return push_equip_type - 0:ANDROID 1:IOS 2:微信 3：WP
   */
  public String getPushEquipType() {
    return pushEquipType;
  }

  /**
   * 设置0:ANDROID 1:IOS 2:微信 3：WP
   *
   * @param pushEquipType 0:ANDROID 1:IOS 2:微信 3：WP
   */
  public void setPushEquipType(String pushEquipType) {
    this.pushEquipType = pushEquipType;
  }

  /**
   * 获取认证项目 1-注册 2-密码设定
   *
   * @return active_item - 认证项目 1-注册 2-密码设定
   */
  public String getActiveItem() {
    return activeItem;
  }

  /**
   * 设置认证项目 1-注册 2-密码设定
   *
   * @param activeItem 认证项目 1-注册 2-密码设定
   */
  public void setActiveItem(String activeItem) {
    this.activeItem = activeItem;
  }
}
