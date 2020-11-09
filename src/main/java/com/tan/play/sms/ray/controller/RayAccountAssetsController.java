package com.tan.play.sms.ray.controller;

import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.PageRequest;
import com.tan.play.sms.ray.entity.RayAccountAssets;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.commons.Constant;
import com.tan.play.sms.ray.entity.commons.JsonResult;
import com.tan.play.sms.ray.entity.commons.PageResult;
import com.tan.play.sms.ray.entity.commons.emun.ResultCode;
import com.tan.play.sms.ray.entity.vo.RechargeVO;
import com.tan.play.sms.ray.service.RayAccountAssetsService;
import com.tan.play.sms.ray.service.RayUserInfoService;
import com.tan.play.sms.ray.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/** @Author 谭婧杰 */
@Slf4j
@Controller
@RequestMapping("/accountAssets")
public class RayAccountAssetsController {

  @Value("${ray.admin.userId}")
  String adminUserID;

  private final RayAccountAssetsService<RayAccountAssets> rayAccountAssetsService;

  private final RayUserInfoService<RayUserinfo> rayUserInfoService;

  public RayAccountAssetsController(
      RayAccountAssetsService<RayAccountAssets> rayAccountAssetsService,
      RayUserInfoService<RayUserinfo> rayUserInfoService) {
    this.rayAccountAssetsService = rayAccountAssetsService;
    this.rayUserInfoService = rayUserInfoService;
  }

  /**
   * @param pageNum 分页开始
   * @param model MODEL
   * @param pageSize 分页大小
   * @param request request
   * @return String
   */
  @GetMapping("/get")
  public String rayAccountAssets(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      Model model,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      HttpServletRequest request) {

    String accountCtimeS = request.getParameter("accountCtimeS");
    String accountCtimeE = request.getParameter("accountCtimeE");
    String rechargePayWay = request.getParameter("payWay");
    String userId = CacheConfig.rayUserInfoCache.getId();
    Condition condition = new Condition(RayAccountAssets.class);
    condition.orderBy("accountCtime").desc();
    Example.Criteria criteria = condition.createCriteria();

    if (!CacheConfig.rayUserInfoCache.getId().equals(adminUserID)) {
      criteria.andEqualTo("accountUserId", userId);
    }

    if (!StringUtils.isEmpty(accountCtimeS)) {
      criteria.andGreaterThanOrEqualTo(
          "accountCtime", accountCtimeS.replaceAll(Constant.REPLACE_CONSTANT, ""));
    }
    if (!StringUtils.isEmpty(accountCtimeE)) {
      criteria.andLessThanOrEqualTo(
          "accountCtime", accountCtimeE.replaceAll(Constant.REPLACE_CONSTANT, ""));
    }
    if (!StringUtils.isEmpty(rechargePayWay)) {
      criteria.andEqualTo("accountPayWay", rechargePayWay);
    }

    PageResult<RayAccountAssets> page =
        rayAccountAssetsService.findPage(new PageRequest(pageNum, pageSize), condition);

    if (page.getTotalPages() > 0) {
      List<RayAccountAssets> content = page.getContent();
      content.stream()
          .map(
              e -> {
                RayUserinfo rayUserInfo =
                    rayUserInfoService.selectByPrimaryKey(e.getAccountUserId());
                if (rayUserInfo != null) {
                  e.setUsername(rayUserInfo.getUsername());
                }
                e.setAccountPayWay(CacheConfig.PayMap.get(e.getAccountPayWay()));
                e.setAccountCtime(DateUtil.strToDateFormat(e.getAccountCtime()));
                return e;
              }).collect(Collectors.toList());
    }
    model.addAttribute("pageResult", page);
    model.addAttribute("payWayList", CacheConfig.dicCache.get("pay_way"));
    model.addAttribute("accountCtimeS", accountCtimeS);
    model.addAttribute("accountCtimeE", accountCtimeE);
    model.addAttribute("rechargePayWay", rechargePayWay);
    return "account/ray-account";
  }

  @PostMapping("/to")
  @ResponseBody
  public JsonResult<RayAccountAssets> recharge(HttpServletRequest request, RechargeVO rechargeVO) {

    JsonResult<RayAccountAssets> jsonResult = new JsonResult<>();
    if (rechargeVO
            .getAccountEnd()
            .replaceAll(Constant.REPLACE_CONSTANT, "")
            .compareTo(rechargeVO.getAccountBegin().replaceAll(Constant.REPLACE_CONSTANT, ""))
        < 0) {
      jsonResult.setSuccess(false);
      jsonResult.setErrorMsg("结束时间不能小于开始时间");
      return jsonResult;
    }

    String userId = request.getParameter("userId");
    if (StringUtils.isEmpty(userId)) {
      jsonResult.setSuccess(false);
      jsonResult.setErrorMsg("用户信息不完整");
      return jsonResult;
    }
    try {
      RayUserinfo rayUserInfo = rayUserInfoService.selectByPrimaryKey(userId);

      if (rayUserInfo != null) {
        log.info(
            "【商务短信平台】 正准备给用户ID为 "
                + rayUserInfo.getId()
                + " 真实姓名为 "
                + rayUserInfo.getUsername()
                + " 充值 ");

        rechargeVO.setRayUserInfo(rayUserInfo);

        jsonResult = rayAccountAssetsService.recharge(rechargeVO);

        log.info("【商务短信平台】 充值金额" + rechargeVO.getRechargePointsTotal() + "成功");
      }
    } catch (Exception e) {
      e.printStackTrace();
      jsonResult.setErrorMsg("出现异常");
      jsonResult.setSuccess(false);
      jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
    }
    return jsonResult;
  }

  @GetMapping("/get/{userId}")
  @ResponseBody
  public JsonResult<RayAccountAssets> getInfo(@PathVariable String userId) {
    JsonResult<RayAccountAssets> jsonResult = new JsonResult<>();
    if (StringUtils.isEmpty(userId)) {
      jsonResult.setErrorMsg("用户ID不能为空");
      jsonResult.setSuccess(false);
      jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
      return jsonResult;
    }
    Example example = new Example(RayAccountAssets.class);
    example.createCriteria().andEqualTo("accountUserId", userId);
    RayAccountAssets rayAccountAssets = rayAccountAssetsService.selectOneByExample(example);
    if (rayAccountAssets != null) {
      jsonResult.setSuccess(true);
      jsonResult.setData(rayAccountAssets);
    }
    return jsonResult;
  }
}
