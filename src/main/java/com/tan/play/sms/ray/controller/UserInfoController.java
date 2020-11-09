package com.tan.play.sms.ray.controller;

import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.*;
import com.tan.play.sms.ray.entity.commons.JsonResult;
import com.tan.play.sms.ray.entity.commons.PageResult;
import com.tan.play.sms.ray.entity.commons.emun.ResultCode;
import com.tan.play.sms.ray.entity.commons.emun.UserCode;
import com.tan.play.sms.ray.entity.vo.UserVO;
import com.tan.play.sms.ray.exception.UserNotFindException;
import com.tan.play.sms.ray.service.RayAccountAssetsService;
import com.tan.play.sms.ray.service.RayRuleInfoService;
import com.tan.play.sms.ray.service.RayUserExtendService;
import com.tan.play.sms.ray.service.RayUserInfoService;
import com.tan.play.sms.ray.utils.DateUtil;
import com.tan.play.sms.ray.utils.MoneyUtils;
import com.tan.play.sms.ray.utils.SnowflakeIdWorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/** @Author 谭婧杰 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserInfoController {

  @Value("${ray.admin.userId}")
  String adminUserID;

  @Value("${ray.admin.phone}")
  String adminUserPhone;

  private final RayUserExtendService<RayUserExtend> rayUserExtendService;

  private final RayUserInfoService<RayUserinfo> rayUserInfoService;

  private final RayAccountAssetsService<RayAccountAssets> rayAccountAssetsService;

  private final RayRuleInfoService<RayRuleInfo> rayRuleInfoService;

  public UserInfoController(
      RayUserInfoService<RayUserinfo> rayUserInfoService,
      RayUserExtendService<RayUserExtend> rayUserExtendService,
      RayAccountAssetsService<RayAccountAssets> rayAccountAssetsService,
      RayRuleInfoService<RayRuleInfo> rayRuleInfoService) {
    this.rayUserInfoService = rayUserInfoService;
    this.rayUserExtendService = rayUserExtendService;
    this.rayAccountAssetsService = rayAccountAssetsService;
    this.rayRuleInfoService = rayRuleInfoService;
  }

  @PostMapping("/register")
  public String register(RayUserinfo rayUserInfo, RayUserExtend rayUserExtend) {

    rayUserInfo.setId(SnowflakeIdWorkerUtil.getInstance().nextId());
    rayUserInfo.setAge("0");
    rayUserInfo.setSex(0);
    // 未充值用户
    rayUserInfo.setStatus(UserCode.USER_PAST_DUE.getStatus());
    rayUserInfo.setCreatedtime(DateUtil.generateTimeStamp());
    rayUserInfo.setPointsLeft(0);
    rayUserInfo.setPointsTotal(0);
    rayUserExtend.setUserId(rayUserInfo.getId());
    rayUserExtend.setUserBirthday(DateUtil.generateTimeStamp());
    rayUserExtend.setUserLevel(1);
    rayUserExtend.setUserPostCode("12345");
    rayUserExtend.setUserCompanyName("默认");
    rayUserExtend.setUserCtime(DateUtil.generateTimeStamp());
    try {
      rayUserInfoService.insert(rayUserInfo);
      rayUserExtendService.insert(rayUserExtend);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "ray_signIn";
  }

  @GetMapping("/get")
  public String getUserInfo(HttpServletRequest request, Model model) {
    String userId = (String) request.getSession().getAttribute("userId");
    if (StringUtils.isEmpty(userId)) {
      throw new UserNotFindException(
          String.valueOf(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode()),
          ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
    }
    UserVO user = rayUserInfoService.findAllProfile(userId);

    model.addAttribute("user", user);
    model.addAttribute("adminUserPhone", adminUserPhone);
    return "/user/ray-profile";
  }

  @GetMapping("/black/get")
  public String getBlack(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      @RequestParam(value = "username", required = false) String name,
      Model model) {

    PageResult<RayUserinfo> date = getDate(1, name, new PageRequest(pageNum, pageSize));
    model.addAttribute("pageResult", date);
    model.addAttribute("username", name);
    model.addAttribute("payWayList", CacheConfig.dicCache.get("pay_way"));
    return "user/ray-black-list";
  }

  @PostMapping("/remove/black/{id}")
  @ResponseBody
  public JsonResult<RayUserinfo> removeBlack(@PathVariable(value = "id") String id) {
    JsonResult<RayUserinfo> jsonResult = new JsonResult<>();
    RayUserinfo rayUserinfo = rayUserInfoService.selectByPrimaryKey(id);
    List<RayRuleInfo> rayRuleInfos = rayRuleInfoService.selectAll();
    if (null != rayRuleInfos
        && rayRuleInfos.size() > 0
        && rayUserinfo.getPointsTotal() >= rayRuleInfos.get(0).getRuleRule()) {
      rayUserinfo.setStatus(UserCode.USER_NORMAL.getStatus());
    } else {
      rayUserinfo.setStatus(UserCode.USER_PAST_DUE.getStatus());
    }
    rayUserinfo.setUpdatedtime(DateUtil.generateTimeStamp());
    Example example = new Example(RayUserExtend.class);
    example.createCriteria().andEqualTo("userId", id);
    try {
      rayUserInfoService.updateByPrimaryKeySelective(rayUserinfo);
      RayUserExtend rayUserExtend = rayUserExtendService.selectOneByExample(example);
      if (rayUserExtend != null) {
        rayUserExtend.setUserUtime(DateUtil.generateTimeStamp());
        rayUserExtend.setUserUserid(CacheConfig.rayAdminInfoCache.getId());

        rayUserExtendService.updateByPrimaryKeySelective(rayUserExtend);
      }
    } catch (Exception e) {
      e.printStackTrace();
      jsonResult.setSuccess(false);
      return jsonResult;
    }
    jsonResult.setSuccess(true);
    return jsonResult;
  }

  @GetMapping("/white/get")
  public String getWhite(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      @RequestParam(value = "username", required = false) String name,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      Model model) {
    PageResult<RayUserinfo> date = getDate(0, name, new PageRequest(pageNum, pageSize));
    model.addAttribute("pageResult", date);
    model.addAttribute("username", name);
    model.addAttribute("payWayList", CacheConfig.dicCache.get("pay_way"));
    return "user/ray-white-list";
  }

  @PostMapping("/update")
  @ResponseBody
  public JsonResult<RayUserinfo> update(HttpServletRequest request, RayUserinfo rayUserInfo) {

    JsonResult<RayUserinfo> result = new JsonResult<>();
    try {
      RayUserinfo oldRayUserInfo = rayUserInfoService.selectByPrimaryKey(rayUserInfo.getId());
      if (oldRayUserInfo == null) {
        result.setSuccess(false);
        result.setErrorMsg(ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        result.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
        return result;
      }
      oldRayUserInfo.setEmail(rayUserInfo.getEmail());
      oldRayUserInfo.setTelnum(rayUserInfo.getTelnum());
      oldRayUserInfo.setPointsTotal(rayUserInfo.getPointsTotal());
      oldRayUserInfo.setPointsLeft(rayUserInfo.getPointsLeft());

      if (!StringUtils.isEmpty(rayUserInfo.getStatus())) {
        oldRayUserInfo.setStatus(rayUserInfo.getStatus());
      } else {
        oldRayUserInfo.setStatus(UserCode.USER_NORMAL.getStatus());
      }
      oldRayUserInfo.setUpdatedtime(DateUtil.generateTimeStamp());
      rayUserInfoService.updateByPrimaryKey(oldRayUserInfo);

      Example example = new Example(RayUserExtend.class);
      example.createCriteria().andEqualTo("userId", oldRayUserInfo.getId());
      RayUserExtend oldRayUserExtend = rayUserExtendService.selectOneByExample(example);
      String uid = request.getParameter("uid");
      if (!uid.equals(oldRayUserExtend.getUid())) {
        oldRayUserExtend.setUid(uid);
        rayUserExtendService.updateByPrimaryKey(oldRayUserExtend);
      }

      result.setSuccess(true);
      result.setData(rayUserInfo);
    } catch (Exception e) {
      e.printStackTrace();
      result.setSuccess(false);
      result.setErrorMsg("发送异常");
    }

    return result;
  }

  @ResponseBody
  @GetMapping("/get/{userId}")
  public JsonResult<UserVO> getUserInfo(@PathVariable String userId) {

    JsonResult<UserVO> jsonResult = new JsonResult<>();
    if (StringUtils.isEmpty(userId)) {
      throw new UserNotFindException(
          String.valueOf(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode()),
          ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
    }
    UserVO user = rayUserInfoService.findAllProfile(userId);
    jsonResult.setSuccess(true);
    jsonResult.setData(user);
    return jsonResult;
  }

  @ResponseBody
  @PostMapping("/update/password")
  public JsonResult<RayUserinfo> updateUserPassWord(
      HttpServletRequest request, HttpServletResponse response) {

    JsonResult<RayUserinfo> jsonResult = new JsonResult<>();
    String userId = request.getParameter("id");
    String oldPassword = request.getParameter("oldPassword");
    String newPassword = request.getParameter("newPassword");
    String newPassword1 = request.getParameter("newPassword1");
    if (StringUtils.isEmpty(userId)) {
      jsonResult.setSuccess(false);
      jsonResult.setErrorMsg(ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
      return jsonResult;
    }
    if (!newPassword.equals(newPassword1)) {
      jsonResult.setErrorCode(ResultCode.USER_CREDENTIALS_EXPIRED.getCode());
      jsonResult.setErrorMsg(ResultCode.USER_CREDENTIALS_EXPIRED.getMessage());
      return jsonResult;
    }
    RayUserinfo oldRayUserInfo = rayUserInfoService.selectByPrimaryKey(userId);
    if (oldRayUserInfo != null && oldRayUserInfo.getPassword().equals(oldPassword)) {
      oldRayUserInfo.setPassword(newPassword);
      oldRayUserInfo.setUpdatedtime(DateUtil.generateTimeStamp());
      rayUserInfoService.updateByPrimaryKey(oldRayUserInfo);
      jsonResult.setSuccess(true);
      jsonResult.setData(oldRayUserInfo);
      return jsonResult;
    }

    return jsonResult;
  }

  /**
   * 分页数据获取
   *
   * @param status 状态 0 正常 1 未充值
   * @param name 搜索条件
   * @param pageRequest 分页请求参数
   * @return PageResult
   */
  private PageResult<RayUserinfo> getDate(int status, String name, PageRequest pageRequest) {
    Condition condition = new Condition(RayUserinfo.class);
    Example.Criteria criteria = condition.createCriteria();
    if (!StringUtils.isEmpty(name)) {
      criteria.andLike("username", "%"+name+"%");
    }
    criteria.andEqualTo("status", status);
    criteria.andNotEqualTo("id", adminUserID);
    condition.orderBy("createdtime").desc();
    PageResult<RayUserinfo> page = rayUserInfoService.findPage(pageRequest, condition);
    Example example = new Example(RayAccountAssets.class);
    if (page.getTotalSize() > 0) {
      List<RayUserinfo> content = page.getContent();
      content.stream()
          .map(
              e -> {
                e.setPointsLeftView(MoneyUtils.parseAmtValue(e.getPointsLeft()));
                e.setPointsTotalView(MoneyUtils.parseAmtValue(e.getPointsTotal()));

                example.createCriteria().andEqualTo("accountUserId", e.getId());
                RayAccountAssets rayAccountAssets =
                    rayAccountAssetsService.selectOneByExample(example);
                if (rayAccountAssets != null) {
                  e.setYuefei(rayAccountAssets.getAccountMonth());
                  // 前端展示赋值

                  e.setBegin(
                      DateUtil.dateToStringPattern(
                          rayAccountAssets.getAccountMonthBegintime(), "yyyymmdd", "yyyy-mm-dd"));
                  e.setEnd(
                      DateUtil.dateToStringPattern(
                          rayAccountAssets.getAccountMonthEndtime(), "yyyymmdd", "yyyy-mm-dd"));

                } else {
                  e.setCreatedtime(null);
                  e.setUpdatedtime(null);
                }
                return e;
              })
          .collect(Collectors.toList());
    }
    return page;
  }
}
