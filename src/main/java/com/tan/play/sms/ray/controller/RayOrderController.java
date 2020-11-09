package com.tan.play.sms.ray.controller;

import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.PageRequest;
import com.tan.play.sms.ray.entity.RayOrder;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.commons.PageResult;
import com.tan.play.sms.ray.service.RayOrderService;
import com.tan.play.sms.ray.service.RayUserInfoService;
import com.tan.play.sms.ray.utils.DateUtil;
import com.tan.play.sms.ray.utils.MoneyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/** @Author 谭婧杰 */
@Slf4j
@Controller
@RequestMapping("/order")
public class RayOrderController {

  @Value("${ray.admin.userId}")
  String adminUserID;

  private final RayOrderService<RayOrder> rayOrderService;

  private final RayUserInfoService<RayUserinfo> rayUserInfoService;

  public RayOrderController(
      RayOrderService<RayOrder> rayOrderService,
      RayUserInfoService<RayUserinfo> rayUserInfoService) {
    this.rayOrderService = rayOrderService;
    this.rayUserInfoService = rayUserInfoService;
  }

  /**
   * 用户订单
   *
   * @param pageNum 分页
   * @param model 返回模型
   * @param pageSize 分页大小
   * @param request request
   * @return String
   */
  @GetMapping("/get")
  public String rayOrder(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      Model model,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      HttpServletRequest request) {

    String orderCtimeS = request.getParameter("orderCtimeS");
    String orderCtimeE = request.getParameter("orderCtimeE");
    Condition condition = new Condition(RayOrder.class);
    condition.orderBy("orderCtime").desc();
    Example.Criteria criteria = condition.createCriteria();
    if (!StringUtils.isEmpty(orderCtimeS)) {
      criteria.andGreaterThanOrEqualTo("orderCtime", orderCtimeS.replaceAll("-","")+"000000");
    }
    if (!StringUtils.isEmpty(orderCtimeE)) {
      criteria.andLessThanOrEqualTo("orderCtime", orderCtimeE.replaceAll("-","")+"000000");
    }

    if (!CacheConfig.rayUserInfoCache.getId().equals(adminUserID)) {
      criteria.andEqualTo("orderUserid", CacheConfig.rayUserInfoCache.getId());
    }

    PageResult<RayOrder> page =
        rayOrderService.findPage(new PageRequest(pageNum, pageSize), condition);
    if (page.getTotalSize() > 0) {
      List<RayOrder> content = page.getContent();

      content.stream()
          .map(
              e -> {
                RayUserinfo rayUserInfo = rayUserInfoService.selectByPrimaryKey(e.getOrderUserid());
                if (rayUserInfo != null) {
                  e.setOrderUserid(rayUserInfo.getUsername());
                }
                e.setOrderPaytype(CacheConfig.PayMap.get(e.getOrderPaytype()));
                e.setOrderFlag(CacheConfig.PayMap.get(e.getOrderFlag()));
                e.setOrderPaytime(DateUtil.strToDateFormat(e.getOrderPaytime()));
                e.setOrderTotalpriceView(MoneyUtils.parseAmtValue(e.getOrderTotalprice()));
                return e;
              })
          .collect(Collectors.toList());
    }

    model.addAttribute("pageResult", page);
    model.addAttribute("orderCtimeE", orderCtimeE);
    model.addAttribute("orderCtimeS", orderCtimeS);

    return "order/ray-order";
  }
}
