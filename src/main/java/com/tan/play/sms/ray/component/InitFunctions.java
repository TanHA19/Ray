package com.tan.play.sms.ray.component;

import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.RayDict;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.service.RayDictService;
import com.tan.play.sms.ray.service.RayUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.stream.Collectors;

/** @Author 谭婧杰 */
@Component
@Order(value = 1)
@Slf4j
public class InitFunctions implements ServletContextAware {

  private final RayDictService<RayDict> rayDictService;
  private final RayUserInfoService<RayUserinfo> rayUserInfoService;

  public InitFunctions(
      RayDictService<RayDict> rayDictService, RayUserInfoService<RayUserinfo> rayUserInfoService) {
    this.rayDictService = rayDictService;
    this.rayUserInfoService = rayUserInfoService;
  }

  @Value("${ray.admin.userId}")
  private String userId;

  @Override
  public void setServletContext(ServletContext servletContext) {
    List<RayDict> rayDictList = rayDictService.selectAll();
    // TODO
    CacheConfig.dicCache =
        rayDictList.stream().collect(Collectors.groupingBy(RayDict::getDictType));
    log.info("【商务短信平台】加载字典表配置信息"+CacheConfig.dicCache);

    log.info("【商务短信平台】加载用户信息");
    CacheConfig.rayAdminInfoCache = rayUserInfoService.selectByPrimaryKey(userId);
  }
}
