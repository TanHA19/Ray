package com.tan.play.sms.ray.controller;

import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.PageRequest;
import com.tan.play.sms.ray.entity.RaySysAnnouncement;
import com.tan.play.sms.ray.entity.commons.JsonResult;
import com.tan.play.sms.ray.entity.commons.PageResult;
import com.tan.play.sms.ray.entity.commons.emun.ResultCode;
import com.tan.play.sms.ray.service.RaySysAnnouncementService;
import com.tan.play.sms.ray.utils.DateUtil;
import com.tan.play.sms.ray.utils.SnowflakeIdWorkerUtil;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/sys/announcement")
@Controller
@Slf4j
public class AnnouncementController {

  private final RaySysAnnouncementService<RaySysAnnouncement> raySysAnnouncementService;

  public AnnouncementController(
      RaySysAnnouncementService<RaySysAnnouncement> raySysAnnouncementService) {
    this.raySysAnnouncementService = raySysAnnouncementService;
  }

  /**
   * 系统配置 - 公告
   *
   * @param pageNum 分页
   * @param model model
   * @param pageSize pageSize
   * @return String
   */
  @GetMapping("/get")
  public String toAnnouncement(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      Model model,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      HttpServletRequest request) {

    String announcementPublishTimeS = request.getParameter("announcementPublishTimeS");
    String announcementPublishTimeE = request.getParameter("announcementPublishTimeE");
    String announcementTitle = request.getParameter("announcementTitle");

    Condition condition = new Condition(RaySysAnnouncement.class);
    condition.orderBy("announcementCreateTime").desc();
    Example.Criteria criteria = condition.createCriteria();
    if (!StringUtils.isEmpty(announcementTitle)) {
      criteria.andLike("announcementTitle", "%"+announcementTitle+"%");
    }
    // 之后发布的 ALL Message
    if (!StringUtils.isEmpty(announcementPublishTimeS)) {
      criteria.andGreaterThanOrEqualTo("announcementPublishTime", announcementPublishTimeS.replaceAll("-","")+"000000");
    }
    if (!StringUtils.isEmpty(announcementPublishTimeE)) {
      criteria.andLessThanOrEqualTo("announcementPublishTime", announcementPublishTimeE.replaceAll("-","")+"000000");
    }
    // 正常
    criteria.andEqualTo("announcementState", "0");
    PageResult<RaySysAnnouncement> page =
        raySysAnnouncementService.findPage(new PageRequest(pageNum, pageSize), condition);

    if (page.getTotalSize() > 0) {

      List<RaySysAnnouncement> raySysAnnouncementList = page.getContent();
      raySysAnnouncementList.stream()
          .map(
              e -> {
                e.setAnnouncementPublishTime(
                    DateUtil.strToDateFormat(e.getAnnouncementPublishTime()));
                return e;
              })
          .collect(Collectors.toList());
    }
    model.addAttribute("pageResult", page);
    model.addAttribute("announcementPublishTimeS", announcementPublishTimeS);
    model.addAttribute("announcementPublishTimeE", announcementPublishTimeE);
    model.addAttribute("announcementTitle", announcementTitle);
    return "sys/announcement/announcement_list";
  }

  @PostMapping("/add")
  @ResponseBody
  public JsonResult<RaySysAnnouncement> addAnnouncement(RaySysAnnouncement raySysAnnouncement) {
    JsonResult<RaySysAnnouncement> jsonResult = new JsonResult();

    if (StringUtils.isEmpty(raySysAnnouncement.getAnnouncementTitle())
        || StringUtils.isEmpty(raySysAnnouncement.getAnnouncementContent())) {

      jsonResult.setSuccess(false);
      jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
      jsonResult.setErrorMsg("请输入公告内容");
      jsonResult.setData(new RaySysAnnouncement());
      return jsonResult;
    }

    try {

      // 走修改
      if (StringUtils.isEmpty(raySysAnnouncement.getAnnouncementId())) {
        raySysAnnouncement.setAnnouncementId(SnowflakeIdWorkerUtil.getInstance().nextId());
        raySysAnnouncement.setAnnouncementUserid(CacheConfig.rayAdminInfoCache.getId());
        // 公告类型
        raySysAnnouncement.setAnnouncementType("1");
        raySysAnnouncement.setAnnouncementState("0");
        raySysAnnouncement.setAnnouncementCreateTime(DateUtil.generateTimeStamp());
        raySysAnnouncement.setAnnouncementPublishTime(DateUtil.generateTimeStamp());
        raySysAnnouncementService.insert(raySysAnnouncement);
        jsonResult.setSuccess(true);
        jsonResult.setData(raySysAnnouncement);
        jsonResult.setErrorMsg("添加公告成功");
      } else {

        jsonResult = updateAnnouncement(raySysAnnouncement);
      }

    } catch (Exception e) {
      e.printStackTrace();
      jsonResult.setSuccess(false);
      jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
      jsonResult.setErrorMsg("发送异常");
    }

    return jsonResult;
  }

  public JsonResult<RaySysAnnouncement> updateAnnouncement(RaySysAnnouncement raySysAnnouncement) {

    JsonResult<RaySysAnnouncement> jsonResult = new JsonResult<>();

    String announcementId = raySysAnnouncement.getAnnouncementId();
    try {
      RaySysAnnouncement oldRaySysAnnouncement =
          raySysAnnouncementService.selectByPrimaryKey(announcementId);
      if (oldRaySysAnnouncement == null) {
        jsonResult.setErrorMsg("找不该到公告信息，不能修改");
        jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
      }
      assert oldRaySysAnnouncement != null;

      if (!raySysAnnouncement
          .getAnnouncementTitle()
          .equals(oldRaySysAnnouncement.getAnnouncementTitle())) {
        oldRaySysAnnouncement.setAnnouncementTitle(raySysAnnouncement.getAnnouncementTitle());
      }
      if (!raySysAnnouncement
          .getAnnouncementContent()
          .equals(oldRaySysAnnouncement.getAnnouncementContent())) {
        oldRaySysAnnouncement.setAnnouncementContent(raySysAnnouncement.getAnnouncementContent());
      }

      oldRaySysAnnouncement.setAnnouncementUpdateTime(DateUtil.generateTimeStamp());
      oldRaySysAnnouncement.setAnnouncementPublishTime(DateUtil.generateTimeStamp());
      oldRaySysAnnouncement.setAnnouncementUpdateUser(CacheConfig.rayAdminInfoCache.getId());

      raySysAnnouncementService.updateByPrimaryKeySelective(oldRaySysAnnouncement);
    } catch (Exception e) {
      e.printStackTrace();
      jsonResult.setSuccess(false);
      jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
      jsonResult.setErrorMsg("发送异常");
      return jsonResult;
    }
    jsonResult.setSuccess(true);
    jsonResult.setErrorMsg("修改公告成功");
    return jsonResult;
  }

  @PostMapping("/delete")
  @ResponseBody
  public JsonResult<RaySysAnnouncement> deleteAnnouncement(RaySysAnnouncement raySysAnnouncement) {

    JsonResult<RaySysAnnouncement> jsonResult = new JsonResult<>();

    String announcementId = raySysAnnouncement.getAnnouncementId();
    try {
      RaySysAnnouncement oldRaySysAnnouncement =
          raySysAnnouncementService.selectByPrimaryKey(announcementId);
      if (oldRaySysAnnouncement == null) {
        jsonResult.setErrorMsg("找不该到公告信息，不能删除");
        jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
        return jsonResult;
      }

      oldRaySysAnnouncement.setAnnouncementUpdateTime(DateUtil.generateTimeStamp());
      oldRaySysAnnouncement.setAnnouncementPublishTime(DateUtil.generateTimeStamp());
      oldRaySysAnnouncement.setAnnouncementUpdateUser(CacheConfig.rayAdminInfoCache.getId());
      oldRaySysAnnouncement.setAnnouncementState("1");
      raySysAnnouncementService.updateByPrimaryKeySelective(oldRaySysAnnouncement);
    } catch (Exception e) {
      e.printStackTrace();
      jsonResult.setSuccess(false);
      jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
      jsonResult.setErrorMsg("发送异常");
      return jsonResult;
    }
    jsonResult.setSuccess(true);
    jsonResult.setErrorMsg("删除成功");
    return jsonResult;
  }
}
