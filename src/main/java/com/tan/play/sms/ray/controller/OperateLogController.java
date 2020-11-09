package com.tan.play.sms.ray.controller;

import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.PageRequest;
import com.tan.play.sms.ray.entity.RayMessageOperateLog;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.commons.JsonResult;
import com.tan.play.sms.ray.entity.commons.PageResult;
import com.tan.play.sms.ray.entity.commons.emun.ResultCode;
import com.tan.play.sms.ray.service.RayMessageOperateLogService;
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

import java.util.List;
import java.util.stream.Collectors;

/** @Author 谭婧杰 */
@Slf4j
@Controller
@RequestMapping("/operateLog")
public class OperateLogController {

  @Value("${ray.admin.userId}")
  String adminUserID;

  private final RayUserInfoService<RayUserinfo> rayUserInfoService;
  private final RayMessageOperateLogService<RayMessageOperateLog> rayMessageOperateLogService;

  public OperateLogController(
      RayMessageOperateLogService<RayMessageOperateLog> rayMessageOperateLogService,
      RayUserInfoService<RayUserinfo> rayUserInfoService) {
    this.rayMessageOperateLogService = rayMessageOperateLogService;
    this.rayUserInfoService = rayUserInfoService;
  }

  @GetMapping("/get")
  public String operateLog(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      @RequestParam(value = "createTimeS", required = false) String createTimeS,
      @RequestParam(value = "createTimeE", required = false) String createTimeE,
      Model model) {

    PageResult<RayMessageOperateLog> date =
        getDate(createTimeS, createTimeE, "", new PageRequest(pageNum, pageSize), false);

    model.addAttribute("pageResult", date);
    model.addAttribute("createTimeS", createTimeS);
    model.addAttribute("createTimeE", createTimeE);

    return "log/ray_message_operate_log_list";
  }

  @DeleteMapping("/delete")
  @ResponseBody
  public JsonResult<RayMessageOperateLog> delete(@RequestParam("id") String id) {

    JsonResult<RayMessageOperateLog> jsonResult = new JsonResult<>();

    RayMessageOperateLog oldRayMessageOperateLog =
        rayMessageOperateLogService.selectByPrimaryKey(id);
    if (oldRayMessageOperateLog != null) {
      oldRayMessageOperateLog.setStatus(0);
      rayMessageOperateLogService.updateByPrimaryKey(oldRayMessageOperateLog);
      jsonResult.setSuccess(true);
      return jsonResult;
    }
    jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
    jsonResult.setErrorMsg(ResultCode.COMMON_FAIL.getMessage());
    return jsonResult;
  }

  @GetMapping("/batch/get")
  public String operateLogBatch(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      @RequestParam(value = "createTimeS", required = false) String createTimeS,
      @RequestParam(value = "createTimeE", required = false) String createTimeE,
      @RequestParam(value = "messageBatch", required = false) String messageBatch,
      Model model) {

    PageResult<RayMessageOperateLog> date =
        getDate(createTimeS, createTimeE, messageBatch, new PageRequest(pageNum, pageSize), true);

    model.addAttribute("pageResult", date);
    model.addAttribute("createTimeS", createTimeS);
    model.addAttribute("createTimeE", createTimeE);

    return "log/ray_message_operate_batch_list";
  }


  /**
   * 获取数据
   *
   * @param createTimeS 搜索条件 创建时间开始
   * @param createTimeE 搜索条件 创建时间结束
   * @param pageRequest 页面请求
   * @param flag 标志位
   * @return 分页结果
   */
  private PageResult<RayMessageOperateLog> getDate(
      String createTimeS,
      String createTimeE,
      String messageBatch,
      PageRequest pageRequest,
      Boolean flag) {
    Condition condition = new Condition(RayMessageOperateLog.class);
    condition.orderBy("createTime").desc();
    Example.Criteria criteria = condition.createCriteria();
    if (!StringUtils.isEmpty(createTimeS)) {
      criteria.andGreaterThanOrEqualTo("createTime", createTimeS.replaceAll("-","")+"000000");
    }
    if (!StringUtils.isEmpty(createTimeE)) {
      criteria.andLessThanOrEqualTo("createTime", createTimeE.replaceAll("-","")+"000000");

    }

    if (!CacheConfig.rayUserInfoCache.getId().equals(adminUserID)) {
      criteria.andEqualTo("userId", CacheConfig.rayUserInfoCache.getId());
    }
    criteria.andEqualTo("status", "1");

    if (flag) {
      criteria.andEqualTo("resultFlag", 0);
      criteria.andEqualTo("messageBatch", messageBatch);
    }
    PageResult<RayMessageOperateLog> page = rayMessageOperateLogService.findPage(pageRequest, condition);
    if(page.getTotalSize() > 0) {
      List<RayMessageOperateLog> content = page.getContent();
      content.stream().map(e->{
        RayUserinfo rayUserInfo = rayUserInfoService.selectByPrimaryKey(e.getUserId());
        if (rayUserInfo != null) {
          e.setUserName(rayUserInfo.getUsername());
        }
        e.setCreateTime(DateUtil.strToDateFormat(e.getCreateTime()));
        return e;
      }).collect(Collectors.toList());
    }
    return page;
  }
}
