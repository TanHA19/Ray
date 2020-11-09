package com.tan.play.sms.ray.configuration;

import com.tan.play.sms.ray.entity.RayDict;
import com.tan.play.sms.ray.entity.RayUserinfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @Author 谭婧杰 */
@Component
public class CacheConfig {

  public static HashMap<String, String> PayMap = new HashMap<String, String>();

  static {
    PayMap.put("200", "微信支付");
    PayMap.put("000", "现金支付");
    PayMap.put("400", "支付宝支付");
    PayMap.put("01", "正常");
    PayMap.put("02", "取消");
    PayMap.put("03", "未付款");
  }

  /**
   *  登录用户信息缓存 <用户名字，用户登录日志>
   */
  public static RayUserinfo rayUserInfoCache;

  /**
   *  管理员信息缓存 <用户名字，用户登录日志>
   */
  public static RayUserinfo rayAdminInfoCache;
  /**
   *  配置信息
   */
  public static Map<String, List<RayDict>> dicCache;
}
