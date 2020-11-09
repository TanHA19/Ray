package com.tan.play.sms.ray.service.impl;

import com.tan.play.sms.ray.entity.RayDict;
import com.tan.play.sms.ray.mapper.RayDictMapper;
import com.tan.play.sms.ray.service.RayDictService;
import org.springframework.stereotype.Service;
import utils.MyMapper;

/** @Author tan */
@Service
public class RayDictServiceImpl<T> extends BaseServiceImpl<RayDict>
    implements RayDictService<RayDict> {
  private final RayDictMapper rayDictMapper;

  public RayDictServiceImpl(RayDictMapper rayDictMapper) {
    this.rayDictMapper = rayDictMapper;
  }

  @Override
  public MyMapper<RayDict> getDao() {
    return rayDictMapper;
  }
}
