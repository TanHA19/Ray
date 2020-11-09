package com.tan.play.sms.ray.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "ray_sendsms.ray_account_assets")
public class RayAccountAssets {
  /** 主键 */
  @Id
  @Column(name = "account_id")
  private String accountId;

  /** 账户编码（会员编码+0001） */
  @Column(name = "account_user_id")
  private String accountUserId;

  /** （0正常，2冻结，3注销） */
  @Column(name = "account_status")
  private String accountStatus;

  /** 支付方式 数据字典：200-微信支付-400支付宝支付 000-现金支付 */
  @Column(name = "account_pay_way")
  private String accountPayWay;

  /** 资产子类型 数据字典：1人民币/2美元/3港币 */
  @Column(name = "account_assets_subtype")
  private String accountAssetsSubtype;

  /** 总充值金额（分），每充值一次，进行累加 */
  @Column(name = "account_fundbal_total")
  private Integer accountFundbalTotal;

  /** 余额，用户第一次成功充值后，该余额和总充值金额一致 */
  @Column(name = "account_balance_amt")
  private Integer accountBalanceAmt;

  /** 创建用户ID */
  @Column(name = "account_userid")
  private String accountUserid;

  /** 创建时间 */
  @Column(name = "account_ctime")
  private String accountCtime;

  /** 更新时间 */
  @Column(name = "account_utime")
  private String accountUtime;

  /** 充值月份 */
  @Column(name = "account_month")
  private Integer accountMonth;

  @Column(name = "account_month_begintime")
  private String accountMonthBegintime;

  /** 充值结束日期 */
  @Column(name = "account_month_endtime")
  private String accountMonthEndtime;

  @Transient private String username;

  /**
   * 获取主键
   *
   * @return account_id - 主键
   */
  public String getAccountId() {
    return accountId;
  }

  /**
   * 设置主键
   *
   * @param accountId 主键
   */
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  /**
   * 获取账户编码（会员编码+0001）
   *
   * @return account_user_id - 账户编码（会员编码+0001）
   */
  public String getAccountUserId() {
    return accountUserId;
  }

  /**
   * 设置账户编码（会员编码+0001）
   *
   * @param accountUserId 账户编码（会员编码+0001）
   */
  public void setAccountUserId(String accountUserId) {
    this.accountUserId = accountUserId;
  }

  /**
   * 获取（0正常，2冻结，3注销）
   *
   * @return account_status - （0正常，2冻结，3注销）
   */
  public String getAccountStatus() {
    return accountStatus;
  }

  /**
   * 设置（0正常，2冻结，3注销）
   *
   * @param accountStatus （0正常，2冻结，3注销）
   */
  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
  }

  /**
   * 获取支付方式 数据字典：200-微信支付-400支付宝支付 000-现金支付
   *
   * @return account_pay_way - 支付方式 数据字典：200-微信支付-400支付宝支付 000-现金支付
   */
  public String getAccountPayWay() {
    return accountPayWay;
  }

  /**
   * 设置支付方式 数据字典：200-微信支付-400支付宝支付 000-现金支付
   *
   * @param accountPayWay 支付方式 数据字典：200-微信支付-400支付宝支付 000-现金支付
   */
  public void setAccountPayWay(String accountPayWay) {
    this.accountPayWay = accountPayWay;
  }

  /**
   * 获取资产子类型 数据字典：1人民币/2美元/3港币
   *
   * @return account_assets_subtype - 资产子类型 数据字典：1人民币/2美元/3港币
   */
  public String getAccountAssetsSubtype() {
    return accountAssetsSubtype;
  }

  /**
   * 设置资产子类型 数据字典：1人民币/2美元/3港币
   *
   * @param accountAssetsSubtype 资产子类型 数据字典：1人民币/2美元/3港币
   */
  public void setAccountAssetsSubtype(String accountAssetsSubtype) {
    this.accountAssetsSubtype = accountAssetsSubtype;
  }

  /**
   * 获取总充值金额（分），每充值一次，进行累加
   *
   * @return account_fundbal_total - 总充值金额（分），每充值一次，进行累加
   */
  public Integer getAccountFundbalTotal() {
    return accountFundbalTotal;
  }

  /**
   * 设置总充值金额（分），每充值一次，进行累加
   *
   * @param accountFundbalTotal 总充值金额（分），每充值一次，进行累加
   */
  public void setAccountFundbalTotal(Integer accountFundbalTotal) {
    this.accountFundbalTotal = accountFundbalTotal;
  }

  /**
   * 获取余额，用户第一次成功充值后，该余额和总充值金额一致
   *
   * @return account_balance_amt - 余额，用户第一次成功充值后，该余额和总充值金额一致
   */
  public Integer getAccountBalanceAmt() {
    return accountBalanceAmt;
  }

  /**
   * 设置余额，用户第一次成功充值后，该余额和总充值金额一致
   *
   * @param accountBalanceAmt 余额，用户第一次成功充值后，该余额和总充值金额一致
   */
  public void setAccountBalanceAmt(Integer accountBalanceAmt) {
    this.accountBalanceAmt = accountBalanceAmt;
  }

  /**
   * 获取创建用户ID
   *
   * @return account_userid - 创建用户ID
   */
  public String getAccountUserid() {
    return accountUserid;
  }

  /**
   * 设置创建用户ID
   *
   * @param accountUserid 创建用户ID
   */
  public void setAccountUserid(String accountUserid) {
    this.accountUserid = accountUserid;
  }

  /**
   * 获取创建时间
   *
   * @return account_ctime - 创建时间
   */
  public String getAccountCtime() {
    return accountCtime;
  }

  /**
   * 设置创建时间
   *
   * @param accountCtime 创建时间
   */
  public void setAccountCtime(String accountCtime) {
    this.accountCtime = accountCtime;
  }

  /**
   * 获取更新时间
   *
   * @return account_utime - 更新时间
   */
  public String getAccountUtime() {
    return accountUtime;
  }

  /**
   * 设置更新时间
   *
   * @param accountUtime 更新时间
   */
  public void setAccountUtime(String accountUtime) {
    this.accountUtime = accountUtime;
  }

  /**
   * 获取充值月份
   *
   * @return account_month - 充值月份
   */
  public Integer getAccountMonth() {
    return accountMonth;
  }

  /**
   * 设置充值月份
   *
   * @param accountMonth 充值月份
   */
  public void setAccountMonth(Integer accountMonth) {
    this.accountMonth = accountMonth;
  }

  /** @return account_month_begintime */
  public String getAccountMonthBegintime() {
    return accountMonthBegintime;
  }

  /** @param accountMonthBegintime */
  public void setAccountMonthBegintime(String accountMonthBegintime) {
    this.accountMonthBegintime = accountMonthBegintime;
  }

  /**
   * 获取充值结束日期
   *
   * @return account_month_endtime - 充值结束日期
   */
  public String getAccountMonthEndtime() {
    return accountMonthEndtime;
  }

  /**
   * 设置充值结束日期
   *
   * @param accountMonthEndtime 充值结束日期
   */
  public void setAccountMonthEndtime(String accountMonthEndtime) {
    this.accountMonthEndtime = accountMonthEndtime;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
