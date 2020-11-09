package com.tan.play.sms.ray.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tan.play.sms.ray.entity.PageRequest;
import com.tan.play.sms.ray.entity.commons.PageResult;
import com.tan.play.sms.ray.exception.SqlCommonsException;
import com.tan.play.sms.ray.service.BaseService;
import com.tan.play.sms.ray.utils.PageUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionException;
import tk.mybatis.mapper.entity.Example;
import utils.MyMapper;

import java.util.List;

/** @Author tan */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

  @Override
  public MyMapper<T> getDao() {
    return null;
  }

  @Override
  public PageResult findPage(PageRequest pageRequest, Example example) throws SqlCommonsException {
    return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest, example));
  }

  @Override
  public PageInfo<T> getPageInfo(PageRequest pageRequest, Example example) {
    int pageNum = pageRequest.getPageNum();
    int pageSize = pageRequest.getPageSize();
    PageHelper.startPage(pageNum, pageSize);
    return new PageInfo<T>(getDao().selectByExample(example));
  }

  @Override
  public int insert(T t) throws SqlSessionException {
    return getDao().insert(t);
  }

  @Override
  public int insertSelective(T t) throws SqlCommonsException {
    return getDao().insertSelective(t);
  }

  @Override
  public int delete(T t) throws SqlCommonsException {
    return getDao().delete(t);
  }

  @Override
  public int deleteByPrimaryKey(Object key) {
    return getDao().deleteByPrimaryKey(key);
  }

  @Override
  public int updateByPrimaryKey(T t) throws SqlCommonsException {
    return getDao().updateByPrimaryKey(t);
  }

  @Override
  public int updateByPrimaryKeySelective(T t) throws SqlCommonsException {
    return getDao().updateByPrimaryKeySelective(t);
  }

  @Override
  public List<T> selectAll() throws SqlSessionException {

    return getDao().selectAll();
  }

  @Override
  public boolean existsWithPrimaryKey(Object key) {
    return getDao().existsWithPrimaryKey(key);
  }

  @Override
  public T selectByPrimaryKey(Object key) {
    return getDao().selectByPrimaryKey(key);
  }

  @Override
  public int selectCount(T t) {
    return getDao().selectCount(t);
  }

  @Override
  public List<T> select(T t) {
    return getDao().select(t);
  }

  @Override
  public T selectOne(T t) {
    return getDao().selectOne(t);
  }

  @Override
  public int deleteByExample(Object example) {
    return getDao().deleteByExample(example);
  }

  @Override
  public List<T> selectByExample(Object example) {
    return getDao().selectByExample(example);
  }

  @Override
  public int selectCountByExample(Object example) {
    return getDao().selectCountByExample(example);
  }

  @Override
  public T selectOneByExample(Object example) {
    return getDao().selectOneByExample(example);
  }

  @Override
  public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
    return getDao().selectByExampleAndRowBounds(example, rowBounds);
  }

  @Override
  public int updateByExample(T record, Object example) {
    return getDao().updateByExample(record, example);
  }

  @Override
  public int updateByExampleSelective(T record, Object example) {
    return 0;
  }

  @Override
  public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
    return getDao().selectByRowBounds(record, rowBounds);
  }

  @Override
  public int insertList(List<T> recordList) {
    return getDao().insertList(recordList);
  }

  @Override
  public int insertUseGeneratedKeys(T record) {
    return getDao().insertUseGeneratedKeys(record);
  }
}
