package com.tan.play.sms.ray.service;

import com.tan.play.sms.ray.entity.RayOrder;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author 谭婧杰
 */
public interface RayOrderService<T> extends BaseService<T> {
    /**
     * 每周订单分析
     * @param example
     * @return
     */
    List<RayOrder> analyseWeek(Example example);
}
