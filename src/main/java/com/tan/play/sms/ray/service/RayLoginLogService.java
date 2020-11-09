package com.tan.play.sms.ray.service;

import com.tan.play.sms.ray.entity.RayUserinfo;

/** @Author 谭婧杰 */
public interface RayLoginLogService<T> extends BaseService<T> {
  /**
   * 添加日志
   *
   * @param rayUserInfo 用户信息
   */
  void insertOrUpdate(RayUserinfo rayUserInfo);
}
