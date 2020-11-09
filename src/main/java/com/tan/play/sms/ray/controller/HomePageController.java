package com.tan.play.sms.ray.controller;

import com.alibaba.fastjson.JSONObject;
import com.tan.play.sms.ray.entity.RayMessageOperateLog;
import com.tan.play.sms.ray.entity.RayOrder;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.WeekInfoModel;
import com.tan.play.sms.ray.entity.commons.JsonResult;
import com.tan.play.sms.ray.entity.vo.Pie;
import com.tan.play.sms.ray.service.RayMessageOperateLogService;
import com.tan.play.sms.ray.service.RayOrderService;
import com.tan.play.sms.ray.service.RayUserInfoService;
import com.tan.play.sms.ray.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author 谭婧杰
 *
 * @date 2020/10/13
 */
@Controller
@Slf4j
public class HomePageController {

  private final RayOrderService<RayOrder> rayOrderService;

  private final RayUserInfoService<RayUserinfo> rayUserInfoService;

  private final RayMessageOperateLogService<RayMessageOperateLog> rayMessageOperateLogService;

  public HomePageController(
      RayUserInfoService<RayUserinfo> rayUserInfoService,
      RayOrderService<RayOrder> rayOrderService,
      RayMessageOperateLogService<RayMessageOperateLog> rayMessageOperateLogService) {
    this.rayUserInfoService = rayUserInfoService;
    this.rayOrderService = rayOrderService;
    this.rayMessageOperateLogService = rayMessageOperateLogService;
  }

  /**
   * 首页
   *
   * @param mv mv
   * @return String
   */
  @GetMapping("/")
  public String toPage(Model mv) {

    Example example = new Example(RayUserinfo.class);

    // 注册总人数
    int userCount = rayUserInfoService.selectCountByExample(example);
    example.clear();
    example = new Example(RayOrder.class);
    // 订单笔数
    int orderCount = rayOrderService.selectCountByExample(example);
    example.clear();
    example = new Example(RayMessageOperateLog.class);
    // 短信总数
    int messagesCount = rayMessageOperateLogService.selectCountByExample(example);

    log.info(
        "【商务短信平台】首页数据展示 会员注册人数：" + userCount + " 订单总数：" + orderCount + " 短信总数：" + messagesCount);
    mv.addAttribute("orderCount", orderCount);
    mv.addAttribute("userCount", userCount);
    mv.addAttribute("messagesCount", messagesCount);

    return "commons/index";
  }

  /**
   * 登录页
   *
   * @return String
   */
  @GetMapping("/raySignIn")
  public String toLogin() {

    return "ray_signIn";
  }

  /**
   * 首页
   *
   * @return String
   */
  @GetMapping("/toPage")
  public void toPageIndex(HttpServletResponse response) throws IOException {
    response.sendRedirect("/");
  }

  /**
   * 注册页
   *
   * @return String
   */
  @GetMapping("/toRegister")
  public String toRegister() {
    return "register/ray_register";
  }

  @ResponseBody
  @GetMapping("/analyse/week")
  public JsonResult<List> analyse() {

    List<Map<String, Object>> lineList = new ArrayList<>();
    Date lastDate = DateUtil.getLatestDay(new Date(), -90);
    List<WeekInfoModel> thisMonthWeekDate = DateUtil.getThisMonthWeekDate(lastDate, new Date());
    List<Pie> pieList = new ArrayList<>();
    HashMap<Integer, Object> pieMap = new HashMap<>(16);
    JSONObject jsonObject = new JSONObject();
    try {
      thisMonthWeekDate.stream()
          .forEach(
              i -> {
                log.info(i.getEndDateOfWeek() + "=======" + i.getStartDateOfWeek());
                Example example = new Example(RayOrder.class);
                example
                    .createCriteria()
                    .andGreaterThanOrEqualTo("orderCtime", i.getStartDateOfWeek() + "595959")
                    .andLessThanOrEqualTo("orderCtime", i.getEndDateOfWeek() + "595959");
                List<RayOrder> rayOrders = rayOrderService.selectByExample(example);
                if (rayOrders.size() > 0) {
                  HashMap<String, Object> hashMap = new HashMap<>(16);
                  hashMap.put("y", rayOrders.size());
                  hashMap.put(
                      "x",
                      DateUtil.strToDateFormatYY(i.getStartDateOfWeek())
                          + " "
                          + DateUtil.strToDateFormatYY(i.getEndDateOfWeek()));
                  lineList.add(hashMap);
                } else {
                  HashMap<String, Object> hashMap = new HashMap<>(16);
                  hashMap.put("y", 0);
                  hashMap.put(
                      "x",
                      DateUtil.strToDateFormatYY(i.getStartDateOfWeek())
                          + " "
                          + DateUtil.strToDateFormatYY(i.getEndDateOfWeek()));
                  lineList.add(hashMap);
                }
              });
      jsonObject.put("lineStatistical", lineList);
      int count = rayMessageOperateLogService.selectCount(null);
      Example example = new Example(RayMessageOperateLog.class);
      // 发送成功
      example.createCriteria().andEqualTo("resultFlag", 0);
      int i = rayMessageOperateLogService.selectCountByExample(example);

      example.clear();
      example.createCriteria().orIsNull("messageBatch");

      pieList.add( new Pie(rayMessageOperateLogService.selectCountByExample(example), "返回失败"));

      pieList.add( new Pie(i, "发送成功"));
      pieList.add(new Pie(count - i, "发送失败"));
      pieList.add(new Pie(0, "响应失败"));
      jsonObject.put("pieList", pieList);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new JsonResult(true, jsonObject);
  }
}
