package utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author tan
 * @version 1.0.0
 * @date 2020/9/12 11:24
 * @param <T>
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {}
