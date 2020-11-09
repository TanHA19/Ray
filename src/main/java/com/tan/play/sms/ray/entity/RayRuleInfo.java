package com.tan.play.sms.ray.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ray_sendsms.ray_rule_info")
public class RayRuleInfo {
  /** 主键 */
  @Id
  @Column(name = "rule_id")
  private Integer ruleId;

  /** 包月 */
  @Column(name = "rule_free_time")
  private Integer ruleFreeTime;

  /** 规则名称 */
  @Column(name = "rule_name")
  private String ruleName;

  /** 备注 */
  @Column(name = "rule_remark")
  private String ruleRemark;

  /** 收费规则 rule_free_time+价格 */
  @Column(name = "rule_rule")
  private Integer ruleRule;

  /** 状态 0 正常 1 删除 */
  @Column(name = "rule_FLAG")
  private String ruleFlag;

  /** 创建用户ID */
  @Column(name = "rule_USERID")
  private String ruleUserid;

  @Column(name = "rule_CTIME")
  private String ruleCtime;

  @Column(name = "rule_UTIME")
  private String ruleUtime;

  /**
   * 获取主键
   *
   * @return rule_id - 主键
   */
  public Integer getRuleId() {
    return ruleId;
  }

  /**
   * 设置主键
   *
   * @param ruleId 主键
   */
  public void setRuleId(Integer ruleId) {
    this.ruleId = ruleId;
  }

  /**
   * 获取包月
   *
   * @return rule_free_time - 包月
   */
  public Integer getRuleFreeTime() {
    return ruleFreeTime;
  }

  /**
   * 设置包月
   *
   * @param ruleFreeTime 包月
   */
  public void setRuleFreeTime(Integer ruleFreeTime) {
    this.ruleFreeTime = ruleFreeTime;
  }

  /**
   * 获取规则名称
   *
   * @return rule_name - 规则名称
   */
  public String getRuleName() {
    return ruleName;
  }

  /**
   * 设置规则名称
   *
   * @param ruleName 规则名称
   */
  public void setRuleName(String ruleName) {
    this.ruleName = ruleName;
  }

  /**
   * 获取备注
   *
   * @return rule_remark - 备注
   */
  public String getRuleRemark() {
    return ruleRemark;
  }

  /**
   * 设置备注
   *
   * @param ruleRemark 备注
   */
  public void setRuleRemark(String ruleRemark) {
    this.ruleRemark = ruleRemark;
  }

  /**
   * 获取收费规则 rule_free_time+价格
   *
   * @return rule_rule - 收费规则 rule_free_time+价格
   */
  public Integer getRuleRule() {
    return ruleRule;
  }

  /**
   * 设置收费规则 rule_free_time+价格
   *
   * @param ruleRule 收费规则 rule_free_time+价格
   */
  public void setRuleRule(Integer ruleRule) {
    this.ruleRule = ruleRule;
  }

  /**
   * 获取状态 0 正常 1 删除
   *
   * @return rule_FLAG - 状态 0 正常 1 删除
   */
  public String getRuleFlag() {
    return ruleFlag;
  }

  /**
   * 设置状态 0 正常 1 删除
   *
   * @param ruleFlag 状态 0 正常 1 删除
   */
  public void setRuleFlag(String ruleFlag) {
    this.ruleFlag = ruleFlag;
  }

  /**
   * 获取创建用户ID
   *
   * @return rule_USERID - 创建用户ID
   */
  public String getRuleUserid() {
    return ruleUserid;
  }

  /**
   * 设置创建用户ID
   *
   * @param ruleUserid 创建用户ID
   */
  public void setRuleUserid(String ruleUserid) {
    this.ruleUserid = ruleUserid;
  }

  /** @return rule_CTIME */
  public String getRuleCtime() {
    return ruleCtime;
  }

  /** @param ruleCtime */
  public void setRuleCtime(String ruleCtime) {
    this.ruleCtime = ruleCtime;
  }

  /** @return rule_UTIME */
  public String getRuleUtime() {
    return ruleUtime;
  }

  /** @param ruleUtime */
  public void setRuleUtime(String ruleUtime) {
    this.ruleUtime = ruleUtime;
  }
}
