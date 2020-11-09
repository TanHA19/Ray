package com.tan.play.sms.ray.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 雪花id生成
 *
 * @author 谭婧杰
 */
public class SnowflakeIdWorkerUtil {

  private static SnowflakeIdWorkerUtil instance;

  public static SnowflakeIdWorkerUtil getInstance() {
    if (null == instance) {
      synchronized (SnowflakeIdWorkerUtil.class) {
        instance = new SnowflakeIdWorkerUtil();
      }
    }
    return instance;
  }

  /**
   * 获得下一个ID (该方法是线程安全的)
   *
   * @return SnowflakeId
   */
  public String nextId() {
    Snowflake snowflake = IdUtil.createSnowflake(1, 1);
    long nextId = snowflake.nextId();
    String nowTime = DateUtil.getNowTimes3();
    return Convert.toStr(nowTime + nextId);
  }
}
