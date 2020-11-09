package com.tan.play.sms.ray.service.impl;

import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.RayMessageOperateLog;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.commons.emun.UserCode;
import com.tan.play.sms.ray.entity.vo.SendMessageRequest;
import com.tan.play.sms.ray.exception.CommonEnum;
import com.tan.play.sms.ray.mapper.RayMessageOperateLogMapper;
import com.tan.play.sms.ray.service.SendMessageService;
import com.tan.play.sms.ray.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** @Author 谭婧杰 */
@Service
public class SendMessageServiceImpl<T> implements SendMessageService {

  @Value("${ray.send.message.url}")
  public String url;

  private final RayMessageOperateLogMapper rayMessageOperateLogMapper;

  public SendMessageServiceImpl(RayMessageOperateLogMapper rayMessageOperateLogMapper) {
    this.rayMessageOperateLogMapper = rayMessageOperateLogMapper;
  }

  @Override
  public String sendMessage(SendMessageRequest sendMessageRequest) {
    String requestUrl = "http://sms.jpsw666.com:18002/send.do";
    String cuttingSymbol = ",";
    String phoneNumStr = sendMessageRequest.getPhoneNumStr();
    if (StringUtils.isEmpty(url)) {
      url = requestUrl;
    }
    RayUserinfo rayUserInfo = CacheConfig.rayUserInfoCache;

    try {
      if (rayUserInfo.getStatus().equals(UserCode.USER_NORMAL.getStatus())
          && !StringUtils.isEmpty(rayUserInfo.getClientpassword()) ) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uid", rayUserInfo.getRemark());
        params.put(
            "pw",
            CommonsUtils.getMD5(
                rayUserInfo.getClientpassword() + DateUtil.getNowTimes3()));
        params.put("mb", phoneNumStr);
        params.put("ms", sendMessageRequest.getPhoneContent());
        params.put("tm", DateUtil.getNowTimes3());
        if (!StringUtils.isEmpty(sendMessageRequest.getSendTimeTask())) {
          params.put("dm", sendMessageRequest.getSendTimeTask());
        }
        String s = HttpClientTool.doPostSms(url, params);
        String[] phoneNumArr = phoneNumStr.split(cuttingSymbol);
        String[] res = s.split(cuttingSymbol);
        Arrays.stream(phoneNumArr)
            .forEach(
                i -> {
                  RayMessageOperateLog rayMessageOperateLog = new RayMessageOperateLog();

                  rayMessageOperateLog.setId(SnowflakeIdWorkerUtil.getInstance().nextId());
                  rayMessageOperateLog.setCreateTime(DateUtil.generateTimeStamp());
                  rayMessageOperateLog.setUserId(rayUserInfo.getId());
                  rayMessageOperateLog.setMessagePhoneContent(i);
                  rayMessageOperateLog.setMessageContent(sendMessageRequest.getPhoneContent());
                  if (res.length <= 1) {
                    rayMessageOperateLog.setFailReason(CommonsUtils.getValue(s));
                    rayMessageOperateLog.setFailReasonCode(s);
                    rayMessageOperateLog.setResultFlag(CommonEnum.SEND_FAIL.getResultCode());
                  } else {
                    rayMessageOperateLog.setResultFlag(CommonEnum.SEND_SUCCESS.getResultCode());
                    rayMessageOperateLog.setMessageBatch(res[1]);
                  }
                  rayMessageOperateLog.setStatus(1);
                  rayMessageOperateLog.setMessageType(sendMessageRequest.getMessageType());
                  rayMessageOperateLogMapper.insert(rayMessageOperateLog);
                });
        return res[0];
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "-99";
    }

    return "";
  }
}
