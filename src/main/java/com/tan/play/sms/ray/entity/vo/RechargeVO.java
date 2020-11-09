package com.tan.play.sms.ray.entity.vo;

import com.tan.play.sms.ray.entity.RayUserinfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @Author 谭婧杰 充值vo */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RechargeVO {

  private RayUserinfo rayUserInfo; // 充值用户
  private Integer rechargePointsTotal; // 充值金额
  private String rechargeUid; // 用户uid
  private String rechargeClientPassword; // 客户端密码
  private String rechargePayWay; // 支付方式
  private String accountEnd; // 充值结束
  private String accountBegin; // 充值开始
  private Integer accountMonth;
}
