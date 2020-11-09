package com.tan.play.sms.ray.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "ray_sendsms.ray_order")
public class RayOrder {
  /** 订单ID */
  @Id
  @Column(name = "ORDER_ID")
  private String orderId;

  /** 订单编码 */
  @Column(name = "ORDER_CODE")
  private String orderCode;

  /** 订单类型: 01短信充值 */
  @Column(name = "ORDER_TYPE")
  private String orderType;

  /** 订单创建时间 */
  @Column(name = "ORDER_CTIME")
  private String orderCtime;

  /** 总金额 */
  @Column(name = "ORDER_TOTALPRICE")
  private Integer orderTotalprice;

  /** 实际付款金额 */
  @Column(name = "ORDER_PRICE")
  private Integer orderPrice;

  /** 优惠金额 */
  @Column(name = "ORDER_COUPON")
  private Integer orderCoupon;

  /** 后付费金额 */
  @Column(name = "ORDER_POST_PAID")
  private Integer orderPostPaid;

  /** 订单支付用户ID */
  @Column(name = "ORDER_USERID")
  private String orderUserid;

  /** 订单状态 01正常02取消03未付款 */
  @Column(name = "ORDER_FLAG")
  private String orderFlag;

  /** 支付方式 */
  @Column(name = "ORDER_PAYTYPE")
  private String orderPaytype;

  /** 付款时间 */
  @Column(name = "ORDER_PAYTIME")
  private String orderPaytime;

  @Transient private Double orderTotalpriceView;

  public Double getOrderTotalpriceView() {
    return orderTotalpriceView;
  }

  public void setOrderTotalpriceView(Double orderTotalpriceView) {
    this.orderTotalpriceView = orderTotalpriceView;
  }

  /**
   * 获取订单ID
   *
   * @return ORDER_ID - 订单ID
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * 设置订单ID
   *
   * @param orderId 订单ID
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * 获取订单编码
   *
   * @return ORDER_CODE - 订单编码
   */
  public String getOrderCode() {
    return orderCode;
  }

  /**
   * 设置订单编码
   *
   * @param orderCode 订单编码
   */
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  /**
   * 获取订单类型: 01短信充值
   *
   * @return ORDER_TYPE - 订单类型: 01短信充值
   */
  public String getOrderType() {
    return orderType;
  }

  /**
   * 设置订单类型: 01短信充值
   *
   * @param orderType 订单类型: 01短信充值
   */
  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  /**
   * 获取订单创建时间
   *
   * @return ORDER_CTIME - 订单创建时间
   */
  public String getOrderCtime() {
    return orderCtime;
  }

  /**
   * 设置订单创建时间
   *
   * @param orderCtime 订单创建时间
   */
  public void setOrderCtime(String orderCtime) {
    this.orderCtime = orderCtime;
  }

  /**
   * 获取总金额
   *
   * @return ORDER_TOTALPRICE - 总金额
   */
  public Integer getOrderTotalprice() {
    return orderTotalprice;
  }

  /**
   * 设置总金额
   *
   * @param orderTotalprice 总金额
   */
  public void setOrderTotalprice(Integer orderTotalprice) {
    this.orderTotalprice = orderTotalprice;
  }

  /**
   * 获取实际付款金额
   *
   * @return ORDER_PRICE - 实际付款金额
   */
  public Integer getOrderPrice() {
    return orderPrice;
  }

  /**
   * 设置实际付款金额
   *
   * @param orderPrice 实际付款金额
   */
  public void setOrderPrice(Integer orderPrice) {
    this.orderPrice = orderPrice;
  }

  /**
   * 获取优惠金额
   *
   * @return ORDER_COUPON - 优惠金额
   */
  public Integer getOrderCoupon() {
    return orderCoupon;
  }

  /**
   * 设置优惠金额
   *
   * @param orderCoupon 优惠金额
   */
  public void setOrderCoupon(Integer orderCoupon) {
    this.orderCoupon = orderCoupon;
  }

  /**
   * 获取后付费金额
   *
   * @return ORDER_POST_PAID - 后付费金额
   */
  public Integer getOrderPostPaid() {
    return orderPostPaid;
  }

  /**
   * 设置后付费金额
   *
   * @param orderPostPaid 后付费金额
   */
  public void setOrderPostPaid(Integer orderPostPaid) {
    this.orderPostPaid = orderPostPaid;
  }

  /**
   * 获取订单支付用户ID
   *
   * @return ORDER_USERID - 订单支付用户ID
   */
  public String getOrderUserid() {
    return orderUserid;
  }

  /**
   * 设置订单支付用户ID
   *
   * @param orderUserid 订单支付用户ID
   */
  public void setOrderUserid(String orderUserid) {
    this.orderUserid = orderUserid;
  }

  /**
   * 获取订单状态 01正常02取消03未付款
   *
   * @return ORDER_FLAG - 订单状态 01正常02取消03未付款
   */
  public String getOrderFlag() {
    return orderFlag;
  }

  /**
   * 设置订单状态 01正常02取消03未付款
   *
   * @param orderFlag 订单状态 01正常02取消03未付款
   */
  public void setOrderFlag(String orderFlag) {
    this.orderFlag = orderFlag;
  }

  /**
   * 获取支付方式
   *
   * @return ORDER_PAYTYPE - 支付方式
   */
  public String getOrderPaytype() {
    return orderPaytype;
  }

  /**
   * 设置支付方式
   *
   * @param orderPaytype 支付方式
   */
  public void setOrderPaytype(String orderPaytype) {
    this.orderPaytype = orderPaytype;
  }

  /**
   * 获取付款时间
   *
   * @return ORDER_PAYTIME - 付款时间
   */
  public String getOrderPaytime() {
    return orderPaytime;
  }

  /**
   * 设置付款时间
   *
   * @param orderPaytime 付款时间
   */
  public void setOrderPaytime(String orderPaytime) {
    this.orderPaytime = orderPaytime;
  }
}
