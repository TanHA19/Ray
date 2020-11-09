package com.tan.play.sms.ray.service;

import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谭婧杰
 */
@Service
public interface RayUserInfoService<T> extends BaseService<T> {
  /**
   * 根据用户昵称或者密码进行登录
   *
   * @param loginName 登录姓名
   * @param password 登录密码
   * @return   List<RayUserInfo>
   */
  List<RayUserinfo> findByUserOrPwd(String loginName, String password);

  /**
   * 获取当前用户全部信息
   *
   * @param userId 用户ID
   * @return UserVO
   */
  UserVO findAllProfile(String userId);
}
