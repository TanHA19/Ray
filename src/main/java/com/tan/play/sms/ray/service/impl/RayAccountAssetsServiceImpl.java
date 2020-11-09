package com.tan.play.sms.ray.service.impl;

import com.tan.play.sms.ray.configuration.CacheConfig;
import com.tan.play.sms.ray.entity.*;
import com.tan.play.sms.ray.entity.commons.Constant;
import com.tan.play.sms.ray.entity.commons.JsonResult;
import com.tan.play.sms.ray.entity.commons.emun.OrderCode;
import com.tan.play.sms.ray.entity.commons.emun.ResultCode;
import com.tan.play.sms.ray.entity.commons.emun.UserCode;
import com.tan.play.sms.ray.entity.vo.RechargeVO;
import com.tan.play.sms.ray.mapper.RayAccountAssetsMapper;
import com.tan.play.sms.ray.mapper.RayOrderMapper;
import com.tan.play.sms.ray.mapper.RayRuleInfoMapper;
import com.tan.play.sms.ray.mapper.RayUserExtendMapper;
import com.tan.play.sms.ray.service.RayAccountAssetsService;
import com.tan.play.sms.ray.service.RayUserInfoService;
import com.tan.play.sms.ray.utils.CommonsUtils;
import com.tan.play.sms.ray.utils.DateUtil;
import com.tan.play.sms.ray.utils.MoneyUtils;
import com.tan.play.sms.ray.utils.SnowflakeIdWorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import utils.MyMapper;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/** @Author tan */
@Service
@Slf4j
public class RayAccountAssetsServiceImpl<T>
    extends BaseServiceImpl<com.tan.play.sms.ray.entity.RayAccountAssets>
    implements RayAccountAssetsService<RayAccountAssets> {

  private final RayAccountAssetsMapper rayAccountAssetsMapper;
  private final RayOrderMapper rayOrderMapper;
  private final RayUserInfoService<RayUserinfo> rayUserInfoService;
  private final RayUserExtendMapper rayUserExtendMapper;
  private final RayRuleInfoMapper rayRuleInfoMapper;
  private RayAccountAssets rayAccountAssets;
  private Integer monthSpace;
  private RechargeVO rechargeVO;

  public RayAccountAssetsServiceImpl(
      RayAccountAssetsMapper rayAccountAssetsMapper,
      RayOrderMapper rayOrderMapper,
      RayUserInfoService<RayUserinfo> rayUserInfoService,
      RayUserExtendMapper rayUserExtendMapper,
      RayRuleInfoMapper rayRuleInfoMapper) {
    this.rayAccountAssetsMapper = rayAccountAssetsMapper;
    this.rayOrderMapper = rayOrderMapper;
    this.rayUserInfoService = rayUserInfoService;
    this.rayUserExtendMapper = rayUserExtendMapper;
    this.rayRuleInfoMapper = rayRuleInfoMapper;
  }

  @Override
  public MyMapper<RayAccountAssets> getDao() {
    return rayAccountAssetsMapper;
  }

  /**
   * 用户充值
   *
   * @param rechargeVO 充值金额
   * @return JsonResult
   */
  @Override
  @Transactional(rollbackFor = {Exception.class})
  public JsonResult<RayAccountAssets> recharge(RechargeVO rechargeVO) throws ParseException {
    JsonResult<RayAccountAssets> jsonResult = new JsonResult<>();
    // 本次充值月份
    Integer monthSpace = 0;

    if (!isVerify(rechargeVO)) {
      jsonResult.setErrorMsg("参数不正确 ，充值金额，用户UID，用户客户端密码必填");
      jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
    }
    rechargeVO.setRechargePointsTotal(rechargeVO.getRechargePointsTotal() * 100);
    // 获取收费规则
    log.info("获取收费规则");
    List<RayRuleInfo> rayRuleInfoList = rayRuleInfoMapper.selectAll();
    if (rayRuleInfoList == null || rayRuleInfoList.size() <= 0) {
      jsonResult.setErrorCode(ResultCode.COMMON_FAIL.getCode());
      jsonResult.setSuccess(false);
      jsonResult.setErrorMsg("请先配置收费规则");
      return jsonResult;
    }
    RayRuleInfo rayRuleInfo = rayRuleInfoList.get(0);
    // ========================充值 begin ======================================

    // 立即生效
    if (!StringUtils.isEmpty(rechargeVO.getAccountMonth())) {

      monthSpace = rechargeVO.getAccountMonth();

    } else {

      monthSpace = DateUtil.getMonthSpace(rechargeVO.getAccountBegin(), rechargeVO.getAccountEnd());
    }

    // 用户存储充值记录
    log.info("【商务短信平台】开始生成用户储值记录");
    RayAccountAssets rayAccountAssets = insertRayAccountAssets(rechargeVO, monthSpace);

    // 生成充值订单
    log.info("【商务短信平台】开始生成充值订单 ");
    insertOrder(rechargeVO);

    // 修改用户状态信息
    log.info("【商务短信平台】开始修改用户状态信息 ");
    userAccountAssets(rechargeVO, rayAccountAssets, rayRuleInfo);

    // ========================充值 end ======================================

    jsonResult.setSuccess(true);
    return jsonResult;
  }

  /**
   * 前端校验
   *
   * @param rechargeVO 前端vo类
   * @return false 校验失败
   */
  private Boolean isVerify(RechargeVO rechargeVO) {
    String id = rechargeVO.getRayUserInfo().getId();
    String ignore = "11";
    return !StringUtils.isEmpty(id)
        && !StringUtils.isEmpty(rechargeVO.getRechargeClientPassword())
        && !StringUtils.isEmpty(rechargeVO.getRechargePointsTotal())
        && (!StringUtils.isEmpty(rechargeVO.getRechargePayWay())
            && !rechargeVO.getRechargePayWay().equals(ignore));
  }

  /**
   * 用户存储表
   *
   * @param rechargeVO 前端vo类
   * @param monthSpace 充值月数
   * @return RayAccountAssets
   * @throws ParseException 日期异常
   */
  private RayAccountAssets insertRayAccountAssets(RechargeVO rechargeVO, Integer monthSpace)
      throws ParseException {

    // 判断是不是第一次充值
    String id = rechargeVO.getRayUserInfo().getId();
    Example example = new Example(com.tan.play.sms.ray.entity.RayAccountAssets.class);
    Example.Criteria criteria = example.createCriteria();
    criteria.andEqualTo("accountUserId", id);
    criteria.andEqualTo("accountStatus", "0");

    RayAccountAssets rayAccountAssets = rayAccountAssetsMapper.selectOneByExample(example);

    // 不是第一次充值
    if (rayAccountAssets != null) {
      notFirstRecharge(rayAccountAssets, monthSpace, rechargeVO);
    } else {
      log.info(
          "【商务短信平台】系统查询登录用户名为 "
              + rechargeVO.getRayUserInfo().getLoginname()
              + " 的用户第一次进行充值 金额"
              + MoneyUtils.parseAmtValue(rechargeVO.getRechargePointsTotal())
              + "/元");
      rayAccountAssets = firstRecharge(id, monthSpace, rechargeVO);
    }

    return rayAccountAssets;
  }

  /**
   * 用户第一次充值
   *
   * @param id 用户id
   * @param monthSpace 月份
   * @param rechargeVO 充值前端参数
   */
  private RayAccountAssets firstRecharge(String id, Integer monthSpace, RechargeVO rechargeVO)
      throws ParseException {
    RayAccountAssets rayAccountAssets = new RayAccountAssets();
    rayAccountAssets.setAccountId(SnowflakeIdWorkerUtil.getInstance().nextId());
    rayAccountAssets.setAccountUserId(id);
    rayAccountAssets.setAccountStatus("0");
    // 1人民币/2美元/3港币',
    rayAccountAssets.setAccountAssetsSubtype("1");
    rayAccountAssets.setAccountFundbalTotal(rechargeVO.getRechargePointsTotal());
    rayAccountAssets.setAccountBalanceAmt(rechargeVO.getRechargePointsTotal());
    rayAccountAssets.setAccountUserid(CacheConfig.rayAdminInfoCache.getId());
    rayAccountAssets.setAccountCtime(DateUtil.generateTimeStamp());
    rayAccountAssets.setAccountPayWay(rechargeVO.getRechargePayWay());
    if (StringUtils.isEmpty(rechargeVO.getAccountBegin())) {
      rayAccountAssets.setAccountMonthBegintime(DateUtil.generate8TimeStamp());
    } else {
      rayAccountAssets.setAccountMonthBegintime(
          rechargeVO.getAccountBegin().replaceAll(Constant.REPLACE_CONSTANT, ""));
    }
    if (StringUtils.isEmpty(rechargeVO.getAccountEnd())) {
      rayAccountAssets.setAccountMonthEndtime(
          DateUtil.subMonth(DateUtil.generate8TimeStamp(), monthSpace));
    } else {
      rayAccountAssets.setAccountMonthEndtime(
          rechargeVO.getAccountEnd().replaceAll(Constant.REPLACE_CONSTANT, ""));
    }
    rayAccountAssets.setAccountMonth(monthSpace);
    rayAccountAssetsMapper.insert(rayAccountAssets);
    return rayAccountAssets;
  }

  /**
   * 不是第一次进行充值
   *
   * @param rayAccountAssets 旧充值信息
   * @param monthSpace 相隔月份
   * @param rechargeVO 前端vo
   */
  private void notFirstRecharge(
      RayAccountAssets rayAccountAssets, Integer monthSpace, RechargeVO rechargeVO)
      throws ParseException {
    this.rayAccountAssets = rayAccountAssets;
    this.monthSpace = monthSpace;
    this.rechargeVO = rechargeVO;
    String accountMonthEndTime = "";
    // 判断该用户是否已经过期
    if (!StringUtils.isEmpty(rayAccountAssets.getAccountMonthEndtime())) {
      accountMonthEndTime = rayAccountAssets.getAccountMonthEndtime();
    }
    if (delayTimeValidate(accountMonthEndTime)) {
      log.info("【商务短信平台】该用户已经过期,延期操作");
      // 开始日期设置今天
      if (StringUtils.isEmpty(rechargeVO.getAccountBegin())) {
        rayAccountAssets.setAccountMonthBegintime(DateUtil.generate8TimeStamp());
      } else {
        rayAccountAssets.setAccountMonthBegintime(rechargeVO.getAccountBegin());
      }
      if (StringUtils.isEmpty(rechargeVO.getAccountEnd())) {
        rayAccountAssets.setAccountMonthEndtime(
            DateUtil.subMonth(DateUtil.generate8TimeStamp(), monthSpace));
      } else {
        rayAccountAssets.setAccountMonthEndtime(rechargeVO.getAccountEnd());
      }
    } else {
      rayAccountAssets.setAccountMonthEndtime(
          DateUtil.subMonth(rayAccountAssets.getAccountMonthEndtime(), monthSpace)
              .replaceAll(Constant.REPLACE_CONSTANT, ""));
    }
    log.info(
        "【商务短信平台】系统查询登录用户名为 "
            + rechargeVO.getRayUserInfo().getLoginname()
            + " 的用户剩余金额"
            + MoneyUtils.parseAmtValue(rayAccountAssets.getAccountFundbalTotal())
            + "/元");

    // 余额
    rayAccountAssets.setAccountBalanceAmt(
        rayAccountAssets.getAccountBalanceAmt() + rechargeVO.getRechargePointsTotal());
    // 总金额
    rayAccountAssets.setAccountFundbalTotal(
        rayAccountAssets.getAccountFundbalTotal() + rechargeVO.getRechargePointsTotal());
    rayAccountAssets.setAccountUserid(CacheConfig.rayAdminInfoCache.getId());
    rayAccountAssets.setAccountUtime(DateUtil.generateTimeStamp());
    rayAccountAssets.setAccountPayWay(rechargeVO.getRechargePayWay());
    rayAccountAssets.setAccountMonth(rayAccountAssets.getAccountMonth() + monthSpace);
    rayAccountAssetsMapper.updateByPrimaryKeySelective(rayAccountAssets);
  }

  /**
   * 用户订单表
   *
   * @param rechargeVO vo
   */
  private void insertOrder(RechargeVO rechargeVO) {
    String id = rechargeVO.getRayUserInfo().getId();
    RayOrder rayOrder = new RayOrder();
    rayOrder.setOrderId(SnowflakeIdWorkerUtil.getInstance().nextId());
    rayOrder.setOrderCode(CommonsUtils.generateCode());
    // 订单类型: 01短信充值
    rayOrder.setOrderType(OrderCode.ORDER_TYPE.getStatus());
    rayOrder.setOrderCtime(DateUtil.generateTimeStamp());
    rayOrder.setOrderTotalprice(rechargeVO.getRechargePointsTotal());
    rayOrder.setOrderPrice(rechargeVO.getRechargePointsTotal());
    rayOrder.setOrderCoupon(0);
    rayOrder.setOrderPostPaid(0);
    rayOrder.setOrderUserid(id);
    // 订单状态 01正常02取消03未付款
    rayOrder.setOrderFlag(OrderCode.ORDER_NORMAL.getStatus());
    rayOrder.setOrderPaytype(rechargeVO.getRechargePayWay());
    rayOrder.setOrderPaytime(DateUtil.generateTimeStamp());

    rayOrderMapper.insert(rayOrder);
  }

  /**
   * 修改用户信息表
   *
   * @param rechargeVO vo
   * @param rayAccountAssets 用户存储信息
   * @param rayRuleInfo 收费规则
   */
  private void userAccountAssets(
      RechargeVO rechargeVO, RayAccountAssets rayAccountAssets, RayRuleInfo rayRuleInfo) {
    RayUserinfo rayUserInfo = rechargeVO.getRayUserInfo();
    rayUserInfo.setPointsTotal(rayUserInfo.getPointsTotal() + rechargeVO.getRechargePointsTotal());
    rayUserInfo.setPointsLeft(rayUserInfo.getPointsLeft() + rechargeVO.getRechargePointsTotal());
    rayUserInfo.setClientpassword(rechargeVO.getRechargeClientPassword());
    // 暂时放uid
    rayUserInfo.setRemark(rechargeVO.getRechargeUid());
    rayUserInfo.setUpdatedtime(DateUtil.generateTimeStamp());
    if (!StringUtils.isEmpty(rayAccountAssets.getAccountMonthEndtime())) {
      rayUserInfo.setStatus(UserCode.USER_NORMAL.getStatus());
    } else {
      rayUserInfo.setStatus(UserCode.USER_PAST_DUE.getStatus());
    }

    rayUserInfoService.updateByPrimaryKeySelective(rayUserInfo);

    // 修改用户扩展表信息
    log.info("【商务短信平台】修改用户扩展表信息 ");
    Example exampleUserExtend = new Example(RayUserExtend.class);
    exampleUserExtend.createCriteria().andEqualTo("userId", rayUserInfo.getId());
    RayUserExtend rayUserExtend = rayUserExtendMapper.selectOneByExample(exampleUserExtend);
    if (rayUserExtend != null) {
      rayUserExtend.setUid(rechargeVO.getRechargeUid());
      rayUserExtend.setUserUtime(DateUtil.generateTimeStamp());
      rayUserExtendMapper.updateByPrimaryKeySelective(rayUserExtend);
    }
  }

  /**
   * 是否过期
   *
   * @param endTime 结束时间
   * @return 布尔 true 已经过期
   */
  private Boolean delayTimeValidate(String endTime) {
    try {
      // 判断当前会员卡是否过期 ,时间格式为yyyyMMddDDmmss
      String maxDate = endTime + "235959";
      Date offDate = DateUtil.strToDatePattern(maxDate, "yyyyMMddHHmmss");
      Date currDate = new Date(System.currentTimeMillis());

      // 当前时间大于结束时间。当前卡已过期
      if (currDate.compareTo(offDate) > 0) {
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
