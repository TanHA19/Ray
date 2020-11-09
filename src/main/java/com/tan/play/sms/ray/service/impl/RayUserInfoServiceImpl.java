package com.tan.play.sms.ray.service.impl;

import com.tan.play.sms.ray.entity.RayAccountAssets;
import com.tan.play.sms.ray.entity.RayLoginlog;
import com.tan.play.sms.ray.entity.RayUserExtend;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.vo.UserVO;
import com.tan.play.sms.ray.mapper.RayAccountAssetsMapper;
import com.tan.play.sms.ray.mapper.RayLoginlogMapper;
import com.tan.play.sms.ray.mapper.RayUserExtendMapper;
import com.tan.play.sms.ray.mapper.RayUserinfoMapper;
import com.tan.play.sms.ray.service.RayUserInfoService;
import com.tan.play.sms.ray.utils.MoneyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import utils.MyMapper;

import java.util.List;

/** @Author tan */
@Service
@Slf4j
public class RayUserInfoServiceImpl<T> extends BaseServiceImpl<RayUserinfo>
    implements RayUserInfoService<RayUserinfo> {

  private final RayUserinfoMapper rayUserinfoMapper;
  private final RayUserExtendMapper rayUserExtendMapper;
  private final RayAccountAssetsMapper rayAccountAssetsMapper;
  private final RayLoginlogMapper rayLoginlogMapper;

  public RayUserInfoServiceImpl(
      RayUserinfoMapper rayUserinfoMapper,
      RayUserExtendMapper rayUserExtendMapper,
      RayAccountAssetsMapper rayAccountAssetsMapper,
      RayLoginlogMapper rayLoginlogMapper) {
    this.rayUserinfoMapper = rayUserinfoMapper;
    this.rayUserExtendMapper = rayUserExtendMapper;
    this.rayAccountAssetsMapper = rayAccountAssetsMapper;
    this.rayLoginlogMapper = rayLoginlogMapper;
  }

  @Override
  public MyMapper<RayUserinfo> getDao() {
    return rayUserinfoMapper;
  }

  @Override
  public List<RayUserinfo> findByUserOrPwd(String loginName, String password) {
    Example example = new Example(RayUserinfo.class);
    Assert.notNull(loginName, "登录名字不能为空");

    Example.Criteria criteria = example.createCriteria();
    criteria.andEqualTo("loginname", loginName);

    if (!StringUtils.isEmpty(password)) {
      criteria.andEqualTo("password", DigestUtils.md5DigestAsHex(password.getBytes()));
    }

    return rayUserinfoMapper.selectByExample(example);
  }

  @Override
  public UserVO findAllProfile(String userId) {

    UserVO userVO = new UserVO();
    RayUserinfo rayUserInfo = rayUserinfoMapper.selectByPrimaryKey(userId);
    rayUserInfo.setPointsLeftView(MoneyUtils.parseAmtValue(rayUserInfo.getPointsLeft()));
    rayUserInfo.setPointsTotalView(MoneyUtils.parseAmtValue(rayUserInfo.getPointsTotal()));
    userVO.setRayUserInfo(rayUserInfo);
    Example example = new Example(RayUserExtend.class);
    example.createCriteria().andEqualTo("userId", userId);
    List<RayUserExtend> rayUserExtendsList = rayUserExtendMapper.selectByExample(example);
    if (null != rayUserExtendsList && rayUserExtendsList.size() > 0) {
      userVO.setRayUserExtend(rayUserExtendsList.get(0));
    }

    Example exampleAccountAsset = new Example(RayAccountAssets.class);
    exampleAccountAsset.createCriteria().andEqualTo("accountUserId", userId);
    RayAccountAssets rayAccountAssets =
        rayAccountAssetsMapper.selectOneByExample(exampleAccountAsset);
    if (rayAccountAssets != null) {
      userVO.setRayAccountAssets(rayAccountAssets);
    }

    Example example1 = new Example(RayLoginlog.class);
    example1.createCriteria().andEqualTo("userId", userId);
    RayLoginlog rayLoginlog = rayLoginlogMapper.selectOneByExample(example1);
    userVO.setRayLoginlog(rayLoginlog);
    return userVO;
  }
}
