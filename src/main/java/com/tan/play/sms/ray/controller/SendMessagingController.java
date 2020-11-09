package com.tan.play.sms.ray.controller;

import com.alibaba.fastjson.JSONObject;
import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.PageRequest;
import com.tan.play.sms.ray.entity.RayCommonlyMessage;
import com.tan.play.sms.ray.entity.commons.Constant;
import com.tan.play.sms.ray.entity.commons.PageResult;
import com.tan.play.sms.ray.entity.commons.emun.ResultCode;
import com.tan.play.sms.ray.entity.vo.SendMessageRequest;
import com.tan.play.sms.ray.service.RayCommonlyMessageService;
import com.tan.play.sms.ray.service.SendMessageService;
import com.tan.play.sms.ray.utils.CommonsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;

/** @Author 谭婧杰 */
@Slf4j
@Controller
public class SendMessagingController {
  @Value("${ray.admin.userId}")
  String adminUserID;

  private final SendMessageService sendMessageService;
  private final RayCommonlyMessageService<RayCommonlyMessage> rayCommonlyMessageService;

  public SendMessagingController(
      RayCommonlyMessageService<RayCommonlyMessage> rayCommonlyMessageService,
      SendMessageService sendMessageService) {
    this.rayCommonlyMessageService = rayCommonlyMessageService;
    this.sendMessageService = sendMessageService;
  }

  /**
   * 普通短信发送
   *
   * @param pageNum 分页
   * @param model 返回模型
   * @param pageSize 分页大小
   * @return String
   */
  @GetMapping("/toSendGeneral")
  public String generalSend(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      Model model,
      @RequestParam(value = "pageSize", defaultValue = "1000") int pageSize) {
    String userId = CacheConfig.rayUserInfoCache.getId();

    Condition condition = new Condition(RayCommonlyMessage.class);
    condition.orderBy("messageCreatetime").desc();

    condition.createCriteria().andEqualTo("userMessageId", userId).andEqualTo("messageState", "0");

    PageResult<RayCommonlyMessage> page =
        rayCommonlyMessageService.findPage(new PageRequest(pageNum, pageSize), condition);
    model.addAttribute("pageResult", page);

    return "message/message_general";
  }

  /**
   * 大容量短信发送
   *
   * @return String
   */
  @GetMapping("/toHighCapacity")
  public String toHighCapacity() {
    return "message/message_high_capacity";
  }

  /**
   * 常用短信
   *
   * @param pageNum 分页
   * @param pageSize 分页大小
   * @return String
   * @return HashMap
   */
  @GetMapping("/commonlyMessage")
  @ResponseBody
  public HashMap<String, Object> commonlyMessage(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {

    String userId = CacheConfig.rayUserInfoCache.getId();
    Example example = new Example(RayCommonlyMessage.class);
    Example.Criteria criteria = example.createCriteria();
    if (!CacheConfig.rayUserInfoCache.getId().equals(adminUserID)) {
      criteria.andEqualTo("userMessageId", userId);
    }
    criteria.andEqualTo("messageState", 0);

    PageResult<RayCommonlyMessage> page =
        rayCommonlyMessageService.findPage(new PageRequest(pageNum, pageSize), example);
    HashMap<String, Object> map = new HashMap<>(16);
    map.put("pageResult", page);
    return map;
  }

  /**
   * 短信发送
   *
   * @param sendMessageRequest sendMessageRequest
   * @return JSONObject
   */
  @RequestMapping("/send/generalMessage")
  @ResponseBody
  public JSONObject sendGeneralMessage(SendMessageRequest sendMessageRequest) {
    JSONObject obj = new JSONObject();
    String separator = ",";
    int i = 10;
    String s = "";
    log.info(
        "【商务短信平台】 发送短信请求接口:"
            + " 电话 :"
            + sendMessageRequest.getPhoneNumStr()
            + "内容"
            + sendMessageRequest.getPhoneContent()
            + "定时发送"
            + sendMessageRequest.getSendTimeTask()
            + "发送类型"
            + sendMessageRequest.getSendWay());

    if (sendMessageRequest.getPhoneContent().length() > Constant.MAX_TEXT_LENGTH) {
      obj.put("code", ResultCode.COMMON_FAIL);
      obj.put("msg", "内容字数不能超过" + Constant.MAX_TEXT_LENGTH);
      return obj;
    }

    if (sendMessageRequest.getPhoneNumStr().split(separator).length
        > Constant.MAX_TEXT_LENGTH * i) {
      obj.put("code", ResultCode.COMMON_FAIL);
      obj.put("msg", "手机号码不能超过" + Constant.MAX_TEXT_LENGTH * i + "个");
      return obj;
    }

    try {
      s = sendMessageService.sendMessage(sendMessageRequest);

    } catch (Exception e) {
      e.printStackTrace();
      obj.put("code", ResultCode.COMMON_FAIL.getCode());
      obj.put("msg", ResultCode.COMMON_FAIL.getMessage());
      return obj;
    }

    if (StringUtils.isEmpty(s)) {
      obj.put("code", ResultCode.NO_PERMISSION.getCode());
      obj.put("msg", ResultCode.NO_PERMISSION.getMessage());
      log.info("【商务短信平台】 发送短信验证码 该用户为黑名单用户或者未充值 不能发送短信");
      return obj;
    }

    obj.put("code", s);
    obj.put("msg", CommonsUtils.getValue(s));
    return obj;
  }
}
