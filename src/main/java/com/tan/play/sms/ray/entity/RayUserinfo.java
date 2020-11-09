package com.tan.play.sms.ray.entity;

import javax.persistence.*;

@Table(name = "ray_sendsms.ray_userinfo")
public class RayUserinfo {
  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  /** 年龄 */
  private String age;

  /** 用户真实姓名 */
  private String username;

  /** 用户昵称 */
  private String loginname;

  /** 用户密码 */
  private String password;

  /** 客户端登录密码 */
  private String clientpassword;

  /** 备注 存取用户uid */
  private String remark;

  /** 性别 */
  private Integer sex;

  /** 电话号码 */
  private String telnum;

  /** 电子邮箱 */
  private String email;

  /** 总积分 */
  @Column(name = "points_total")
  private Integer pointsTotal;

  /** 剩余积分 */
  @Column(name = "points_left")
  private Integer pointsLeft;

  /** 0 正常 1 (未充值用户) */
  private Integer status;

  /** 修改时间 */
  @Column(name = "updatedTime")
  private String updatedtime;

  /** 创建时间 */
  @Column(name = "createdTime")
  private String createdtime;

  @Transient private Double pointsTotalView;

  public Double getPointsTotalView() {
    return pointsTotalView;
  }

  public void setPointsTotalView(Double pointsTotalView) {
    this.pointsTotalView = pointsTotalView;
  }

  public Double getPointsLeftView() {
    return pointsLeftView;
  }

  public void setPointsLeftView(Double pointsLeftView) {
    this.pointsLeftView = pointsLeftView;
  }

  public Integer getYuefei() {
    return yuefei;
  }

  public void setYuefei(Integer yuefei) {
    this.yuefei = yuefei;
  }

  public String getBegin() {
    return begin;
  }

  public void setBegin(String begin) {
    this.begin = begin;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  @Transient private Double pointsLeftView;

  @Transient private Integer yuefei;

  @Transient private String begin;

  @Transient private String end;
  /**
   * 获取主键
   *
   * @return id - 主键
   */
  public String getId() {
    return id;
  }

  /**
   * 设置主键
   *
   * @param id 主键
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 获取年龄
   *
   * @return age - 年龄
   */
  public String getAge() {
    return age;
  }

  /**
   * 设置年龄
   *
   * @param age 年龄
   */
  public void setAge(String age) {
    this.age = age;
  }

  /**
   * 获取用户真实姓名
   *
   * @return username - 用户真实姓名
   */
  public String getUsername() {
    return username;
  }

  /**
   * 设置用户真实姓名
   *
   * @param username 用户真实姓名
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * 获取用户昵称
   *
   * @return loginname - 用户昵称
   */
  public String getLoginname() {
    return loginname;
  }

  /**
   * 设置用户昵称
   *
   * @param loginname 用户昵称
   */
  public void setLoginname(String loginname) {
    this.loginname = loginname;
  }

  /**
   * 获取用户密码
   *
   * @return password - 用户密码
   */
  public String getPassword() {
    return password;
  }

  /**
   * 设置用户密码
   *
   * @param password 用户密码
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * 获取客户端登录密码
   *
   * @return clientpassword - 客户端登录密码
   */
  public String getClientpassword() {
    return clientpassword;
  }

  /**
   * 设置客户端登录密码
   *
   * @param clientpassword 客户端登录密码
   */
  public void setClientpassword(String clientpassword) {
    this.clientpassword = clientpassword;
  }

  /**
   * 获取备注 存取用户uid
   *
   * @return remark - 备注 存取用户uid
   */
  public String getRemark() {
    return remark;
  }

  /**
   * 设置备注 存取用户uid
   *
   * @param remark 备注 存取用户uid
   */
  public void setRemark(String remark) {
    this.remark = remark;
  }

  /**
   * 获取性别
   *
   * @return sex - 性别
   */
  public Integer getSex() {
    return sex;
  }

  /**
   * 设置性别
   *
   * @param sex 性别
   */
  public void setSex(Integer sex) {
    this.sex = sex;
  }

  /**
   * 获取电话号码
   *
   * @return telnum - 电话号码
   */
  public String getTelnum() {
    return telnum;
  }

  /**
   * 设置电话号码
   *
   * @param telnum 电话号码
   */
  public void setTelnum(String telnum) {
    this.telnum = telnum;
  }

  /**
   * 获取电子邮箱
   *
   * @return email - 电子邮箱
   */
  public String getEmail() {
    return email;
  }

  /**
   * 设置电子邮箱
   *
   * @param email 电子邮箱
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * 获取总积分
   *
   * @return points_total - 总积分
   */
  public Integer getPointsTotal() {
    return pointsTotal;
  }

  /**
   * 设置总积分
   *
   * @param pointsTotal 总积分
   */
  public void setPointsTotal(Integer pointsTotal) {
    this.pointsTotal = pointsTotal;
  }

  /**
   * 获取剩余积分
   *
   * @return points_left - 剩余积分
   */
  public Integer getPointsLeft() {
    return pointsLeft;
  }

  /**
   * 设置剩余积分
   *
   * @param pointsLeft 剩余积分
   */
  public void setPointsLeft(Integer pointsLeft) {
    this.pointsLeft = pointsLeft;
  }

  /**
   * 获取0 正常 1 (未充值用户)
   *
   * @return status - 0 正常 1 (未充值用户)
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * 设置0 正常 1 (未充值用户)
   *
   * @param status 0 正常 1 (未充值用户)
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * 获取修改时间
   *
   * @return updatedTime - 修改时间
   */
  public String getUpdatedtime() {
    return updatedtime;
  }

  /**
   * 设置修改时间
   *
   * @param updatedtime 修改时间
   */
  public void setUpdatedtime(String updatedtime) {
    this.updatedtime = updatedtime;
  }

  /**
   * 获取创建时间
   *
   * @return createdTime - 创建时间
   */
  public String getCreatedtime() {
    return createdtime;
  }

  /**
   * 设置创建时间
   *
   * @param createdtime 创建时间
   */
  public void setCreatedtime(String createdtime) {
    this.createdtime = createdtime;
  }
}
