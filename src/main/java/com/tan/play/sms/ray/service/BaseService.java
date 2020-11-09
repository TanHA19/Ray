package com.tan.play.sms.ray.service;

import com.github.pagehelper.PageInfo;
import com.tan.play.sms.ray.entity.PageRequest;
import com.tan.play.sms.ray.entity.commons.PageResult;
import com.tan.play.sms.ray.exception.SqlCommonsException;
import tk.mybatis.mapper.entity.Example;
import utils.MyMapper;

/** @Author 谭婧杰 */
public interface BaseService<T> extends MyMapper<T> {

  /**
   * 获取dao
   * @return MyMapper
   */
  MyMapper<T> getDao();

  /**
   * 分页
   * @param pageRequest pageRequest
   * @param example example
   * @return PageResult<T>
   * @throws SqlCommonsException SqlCommonsException
   */
  PageResult<T> findPage(PageRequest pageRequest, Example example) throws SqlCommonsException;
  /**
   * 分页
   * @param pageRequest pageRequest
   * @param example example
   * @return PageInfo<T>
   */
  PageInfo<T> getPageInfo(PageRequest pageRequest, Example example);
}
