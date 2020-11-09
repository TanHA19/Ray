package com.tan.play.sms.ray.service.impl;

import com.tan.play.sms.ray.entity.RayCommonlyMessage;
import com.tan.play.sms.ray.mapper.RayCommonlyMessageMapper;
import com.tan.play.sms.ray.service.RayCommonlyMessageService;
import org.springframework.stereotype.Service;
import utils.MyMapper;

/** @Author 谭婧杰 */
@Service
public class RayCommonlyMessageServiceImpl<T> extends BaseServiceImpl<RayCommonlyMessage>
    implements RayCommonlyMessageService<RayCommonlyMessage> {

  private final RayCommonlyMessageMapper rayCommonlyMessageMapper;

  public RayCommonlyMessageServiceImpl(RayCommonlyMessageMapper rayCommonlyMessageMapper) {
    this.rayCommonlyMessageMapper = rayCommonlyMessageMapper;
  }

  @Override
  public MyMapper<RayCommonlyMessage> getDao() {
    return rayCommonlyMessageMapper;
  }
}
