package com.tan.play.sms.ray.service.impl;

import com.tan.play.sms.ray.entity.RayMessageOperateLog;
import com.tan.play.sms.ray.mapper.RayMessageOperateLogMapper;
import com.tan.play.sms.ray.service.RayMessageOperateLogService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import utils.MyMapper;

import java.util.List;

/** @Author tan */
@Service
public class RayMessageOperateLogServiceImpl<T> extends BaseServiceImpl<RayMessageOperateLog>
    implements RayMessageOperateLogService<RayMessageOperateLog> {
  private final RayMessageOperateLogMapper rayMessageOperateLogMapper;

  public RayMessageOperateLogServiceImpl(RayMessageOperateLogMapper rayMessageOperateLogMapper) {
    this.rayMessageOperateLogMapper = rayMessageOperateLogMapper;
  }

  @Override
  public MyMapper getDao() {
    return rayMessageOperateLogMapper;
  }

  @Override
  public List<RayMessageOperateLog> findInfoByBatch(String messageBatch) {
    Example example = new Example(RayMessageOperateLog.class);
    example.createCriteria().andEqualTo("messageBatch", messageBatch);
    List<RayMessageOperateLog> rayMessageOperateLogList = selectByExample(example);
    return rayMessageOperateLogList;
  }
}
