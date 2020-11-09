package com.tan.play.sms.ray.controller;

import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.PageRequest;
import com.tan.play.sms.ray.entity.RayCommonlyMessage;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.commons.PageResult;
import com.tan.play.sms.ray.entity.vo.ResultVO;
import com.tan.play.sms.ray.exception.CommonEnum;
import com.tan.play.sms.ray.service.RayCommonlyMessageService;
import com.tan.play.sms.ray.service.RayUserInfoService;
import com.tan.play.sms.ray.utils.DateUtil;
import com.tan.play.sms.ray.utils.SnowflakeIdWorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/** @Author 谭婧杰 */
@Slf4j
@Controller
@RequestMapping("commonly/message")
public class CommonlyMessageController {

  @Value("${ray.admin.userId}")
  String adminUserID;

  private final RayUserInfoService<RayUserinfo> rayUserInfoService;
  private final RayCommonlyMessageService<RayCommonlyMessage> rayCommonlyMessageService;

  public CommonlyMessageController(
      RayCommonlyMessageService<RayCommonlyMessage> rayCommonlyMessageService,
      RayUserInfoService<RayUserinfo> rayUserInfoService) {
    this.rayCommonlyMessageService = rayCommonlyMessageService;
    this.rayUserInfoService = rayUserInfoService;
  }

  /**
   * 常用短语
   *
   * @param pageNum pageNum
   * @param model model
   * @param pageSize pageSize
   * @param request request
   * @return String
   */
  @GetMapping("/get")
  public String commonlyMessage(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      Model model,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      HttpServletRequest request) {

    String messagePushContent = request.getParameter("messagePushContent");
    String userId = CacheConfig.rayUserInfoCache.getId();
    Condition condition = new Condition(RayCommonlyMessage.class);
    condition.orderBy("messageCreatetime").desc();
    Example.Criteria criteria = condition.createCriteria();
    if (!CacheConfig.rayUserInfoCache.getId().equals(adminUserID)) {
      criteria.andEqualTo("userMessageId", userId);
    }
    if (!StringUtils.isEmpty(messagePushContent)) {
      criteria.andLike("messagePushContent", "%"+messagePushContent+"%");
    }

    criteria.andEqualTo("messageState", "0");
    PageResult page =
        rayCommonlyMessageService.findPage(new PageRequest(pageNum, pageSize), condition);

    if (page.getTotalPages() > 0) {
      List<RayCommonlyMessage> content = page.getContent();
      content.stream()
          .map(
              e -> {
                String messageCreateTime = e.getMessageCreatetime();
                String messageUpdateTime = e.getMessageUpdatetime();
                if (!StringUtils.isEmpty(messageCreateTime)) {

                  e.setMessageCreatetime(
                      DateUtil.dateToStringPattern(
                          messageCreateTime, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
                }
                if (!StringUtils.isEmpty(messageUpdateTime)) {

                  e.setMessageUpdatetime(
                      DateUtil.dateToStringPattern(
                          messageCreateTime, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
                }

                RayUserinfo rayUserInfo =
                    rayUserInfoService.selectByPrimaryKey(e.getUserMessageId());
                if (rayUserInfo != null) {
                  e.setUserMessageName(rayUserInfo.getUsername());
                }
                return e;
              }).collect(Collectors.toList());
    }
    model.addAttribute("pageResult", page);
    model.addAttribute("messagePushContent", messagePushContent);
    return "message/commonly_message_list";
  }

  @PostMapping("/add")
  @ResponseBody
  public ResultVO<RayCommonlyMessage> add(
      @Validated RayCommonlyMessage rayCommonlyMessage, BindingResult bindingResult) {
    ResultVO<RayCommonlyMessage> resultVO = new ResultVO<>();

    if (bindingResult.hasErrors()) {
      List<ObjectError> errorList = bindingResult.getAllErrors();
      for (ObjectError error : errorList) {

        resultVO.setCode(Integer.valueOf(CommonEnum.FAIL.getResultCode()));
        resultVO.setMessage(error.getDefaultMessage());
        log.error("【商务发票平台】 常用短信保持信息异常 " + error.getDefaultMessage());
        return resultVO;
      }
    }

    try {

      if (StringUtils.isEmpty(rayCommonlyMessage.getMessageId())) {
        rayCommonlyMessage.setMessageId(SnowflakeIdWorkerUtil.getInstance().nextId());
        rayCommonlyMessage.setUserMessageId(CacheConfig.rayUserInfoCache.getId());
        rayCommonlyMessage.setMessageCreatetime(DateUtil.getNowTimes3());
        rayCommonlyMessage.setMessageState("0");
        rayCommonlyMessageService.insert(rayCommonlyMessage);

      } else {
        RayCommonlyMessage rayCommonlyMessageOld =
            rayCommonlyMessageService.selectByPrimaryKey(rayCommonlyMessage.getMessageId());
        if (rayCommonlyMessageOld != null) {
          rayCommonlyMessageOld.setMessagePushContent(rayCommonlyMessage.getMessagePushContent());
          rayCommonlyMessageOld.setMessageUpdatetime(DateUtil.generateTimeStamp());
          rayCommonlyMessageService.updateByPrimaryKeySelective(rayCommonlyMessageOld);
        }
      }
      resultVO.setCode(Integer.valueOf(CommonEnum.SUCCESS.getResultCode()));
      resultVO.setMessage(CommonEnum.SUCCESS.getResultMsg());
    } catch (Exception e) {
      e.printStackTrace();
      resultVO.setCode(Integer.valueOf(CommonEnum.FAIL.getResultCode()));
      resultVO.setMessage(CommonEnum.FAIL.getResultMsg());
    }

    return resultVO;
  }

  @DeleteMapping("/delete")
  @ResponseBody
  public ResultVO<RayCommonlyMessage> delete(HttpServletRequest request) {
    ResultVO<RayCommonlyMessage> resultVO = new ResultVO<>();

    String commonlyMessageId = request.getParameter("commonlyMessageId");
    if (StringUtils.isEmpty(commonlyMessageId)) {
      resultVO.setCode(Integer.valueOf(CommonEnum.FAIL.getResultCode()));
      resultVO.setMessage("短信ID不能为空");
      log.error("【商务发票平台】 常用短信保持信息异常 短信ID为空");
      return resultVO;
    }
    RayCommonlyMessage oldRayCommonlyMessage =
        rayCommonlyMessageService.selectByPrimaryKey(commonlyMessageId);

    if (oldRayCommonlyMessage != null) {
      oldRayCommonlyMessage.setMessageState("1");
      oldRayCommonlyMessage.setMessageUpdatetime(DateUtil.generateTimeStamp());
    }
    try {
      rayCommonlyMessageService.updateByPrimaryKey(oldRayCommonlyMessage);

      resultVO.setCode(Integer.valueOf(CommonEnum.SUCCESS.getResultCode()));
      resultVO.setMessage(CommonEnum.SUCCESS.getResultMsg());
    } catch (Exception e) {
      e.printStackTrace();
      resultVO.setCode(Integer.valueOf(CommonEnum.FAIL.getResultCode()));
      resultVO.setMessage(CommonEnum.FAIL.getResultMsg());
    }

    return resultVO;
  }
}
