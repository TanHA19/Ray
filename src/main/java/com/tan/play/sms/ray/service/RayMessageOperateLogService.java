package com.tan.play.sms.ray.service;

import com.tan.play.sms.ray.entity.RayMessageOperateLog;

import java.util.List;

/**
 * @author 谭婧杰
 */
public interface RayMessageOperateLogService<T> extends BaseService<T> {
  /**
   * 根据批次号寻找
   * @param messageBatch 批次号
   * @return  List<RayMessageOperateLog>
   */
  List<RayMessageOperateLog> findInfoByBatch(String messageBatch);
}
