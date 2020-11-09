package com.tan.play.sms.ray.service;

import com.tan.play.sms.ray.entity.commons.JsonResult;
import com.tan.play.sms.ray.entity.vo.RechargeVO;

import java.text.ParseException;

/**
 * @author 谭婧杰
 */
public  interface RayAccountAssetsService<T> extends BaseService<T> {

  /**
   * 用户充值
   * @param rechargeVO  rechargeVO
   * @return JsonResult<T>
   * @throws ParseException ParseException
   */
  JsonResult<T> recharge(RechargeVO rechargeVO) throws ParseException;
}
