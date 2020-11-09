package com.tan.play.sms.ray.service.impl;

import com.tan.play.sms.ray.entity.RayRuleInfo;
import com.tan.play.sms.ray.mapper.RayRuleInfoMapper;
import com.tan.play.sms.ray.service.RayRuleInfoService;
import org.springframework.stereotype.Service;
import utils.MyMapper;

/** @Author 谭婧杰 */
@Service
public class RayRuleInfoServiceImpl<T> extends BaseServiceImpl<RayRuleInfo>
    implements RayRuleInfoService<RayRuleInfo> {

  private final RayRuleInfoMapper rayRuleInfoMapper;

  public RayRuleInfoServiceImpl(RayRuleInfoMapper rayRuleInfoMapper) {
    this.rayRuleInfoMapper = rayRuleInfoMapper;
  }

  @Override
  public MyMapper<RayRuleInfo> getDao() {
    return rayRuleInfoMapper;
  }
}
