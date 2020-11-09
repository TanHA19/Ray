package com.tan.play.sms.ray.service.impl;

import com.tan.play.sms.ray.entity.RayOrder;
import com.tan.play.sms.ray.mapper.RayOrderMapper;
import com.tan.play.sms.ray.service.RayOrderService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import utils.MyMapper;

import java.util.List;

/** @Author tan */
@Service
public class RayOrderServiceImpl<T> extends BaseServiceImpl<RayOrder>
    implements RayOrderService<RayOrder> {
  private final RayOrderMapper rayOrderMapper;

  public RayOrderServiceImpl(RayOrderMapper rayOrderMapper) {
    this.rayOrderMapper = rayOrderMapper;
  }

  @Override
  public MyMapper<RayOrder> getDao() {
    return rayOrderMapper;
  }

  @Override
  public List<RayOrder> analyseWeek(Example example) {
    return null;
  }
}
