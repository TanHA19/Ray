package com.tan.play.sms.ray.service.impl;

import cn.hutool.core.convert.Convert;
import com.tan.play.sms.ray.entity.RayLoginlog;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.commons.emun.UserCode;
import com.tan.play.sms.ray.mapper.RayLoginlogMapper;
import com.tan.play.sms.ray.service.RayLoginLogService;
import com.tan.play.sms.ray.utils.SnowflakeIdWorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import utils.MyMapper;

import java.net.InetAddress;
import java.util.Date;

/** @Author 谭婧杰 */
@Service
@Slf4j
public class RayLoginLogServiceImpl<T> extends BaseServiceImpl<RayLoginlog>
    implements RayLoginLogService<RayLoginlog> {

  private final RayLoginlogMapper rayLoginlogMappera;

  public RayLoginLogServiceImpl(RayLoginlogMapper rayLoginlogMappera) {
    this.rayLoginlogMappera = rayLoginlogMappera;
  }

  @Override
  public MyMapper<RayLoginlog> getDao() {
    return rayLoginlogMappera;
  }

  @Override
  public void insertOrUpdate(RayUserinfo rayUserInfo) {

    log.info("【商务短信平台】 添加登录日志");

    Example example = new Example(RayLoginlog.class);
    example.createCriteria().andEqualTo("userId", rayUserInfo.getId());

    RayLoginlog OldRayLoginLog = rayLoginlogMappera.selectOneByExample(example);

    try {
      if (OldRayLoginLog != null) {

        OldRayLoginLog.setEmail(rayUserInfo.getEmail());

        OldRayLoginLog.setLoginTatalCt(
            String.valueOf(Convert.toInt(OldRayLoginLog.getLoginTatalCt()) + 1));
        OldRayLoginLog.setLoginPwd(rayUserInfo.getPassword());
        OldRayLoginLog.setPhone(rayUserInfo.getTelnum());
        OldRayLoginLog.setLastDatetime(new Date());
        OldRayLoginLog.setLoginIp(InetAddress.getLocalHost().getHostAddress());
        OldRayLoginLog.setLoginType(String.valueOf(UserCode.USER_PWD_LOGIN.getStatus()));
        rayLoginlogMappera.updateByPrimaryKeySelective(OldRayLoginLog);

      } else {
        OldRayLoginLog = new RayLoginlog();
        OldRayLoginLog.setId(SnowflakeIdWorkerUtil.getInstance().nextId());
        OldRayLoginLog.setUserId(rayUserInfo.getId());
        OldRayLoginLog.setLoginTatalCt("1");
        OldRayLoginLog.setEmail(rayUserInfo.getEmail());
        OldRayLoginLog.setLoginPwd(rayUserInfo.getPassword());
        OldRayLoginLog.setPhone(rayUserInfo.getTelnum());
        OldRayLoginLog.setLastDatetime(new Date());
        OldRayLoginLog.setLoginIp(InetAddress.getLocalHost().getHostAddress());
        OldRayLoginLog.setLoginType(String.valueOf(UserCode.USER_PWD_LOGIN.getStatus()));
        rayLoginlogMappera.insert(OldRayLoginLog);
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
    }
  }
}
