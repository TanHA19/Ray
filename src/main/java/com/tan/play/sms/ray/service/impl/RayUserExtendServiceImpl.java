package com.tan.play.sms.ray.service.impl;

import com.tan.play.sms.ray.entity.RayUserExtend;
import com.tan.play.sms.ray.mapper.RayUserExtendMapper;
import com.tan.play.sms.ray.service.RayUserExtendService;
import org.springframework.stereotype.Service;
import utils.MyMapper;

/** @Author tan */
@Service
public class RayUserExtendServiceImpl<T> extends BaseServiceImpl<RayUserExtend>
    implements RayUserExtendService<RayUserExtend> {

  private final RayUserExtendMapper rayUserExtendMapper;

  public RayUserExtendServiceImpl(RayUserExtendMapper rayUserExtendMapper) {
    this.rayUserExtendMapper = rayUserExtendMapper;
  }

  @Override
  public MyMapper<RayUserExtend> getDao() {
    return rayUserExtendMapper;
  }
}
