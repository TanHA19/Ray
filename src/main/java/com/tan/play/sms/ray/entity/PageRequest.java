package com.tan.play.sms.ray.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 分页查询请求封装类
 *
 * @author 谭婧杰
 */
@AllArgsConstructor
@Data
public class PageRequest {
  /** 当前页码 */
  private int pageNum;
  /** 每页数量 */
  private int pageSize;
}
