package com.tan.play.sms.ray.borws;

import com.tan.play.sms.ray.entity.RayLoginlog;
import com.tan.play.sms.ray.entity.RayUserinfo;
import com.tan.play.sms.ray.entity.commons.emun.ResultCode;
import com.tan.play.sms.ray.exception.UserNotFindException;
import com.tan.play.sms.ray.service.RayLoginLogService;
import com.tan.play.sms.ray.service.RayUserInfoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/** @Author 谭婧杰 */
@Slf4j
@Component("myUserDetailServiceImpl")
public class MyUserDetailServiceImpl implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;
  private final RayUserInfoService<RayUserinfo> rayUserInfoService;
  private final RayLoginLogService<RayLoginlog> rayLoginLogService;

  public MyUserDetailServiceImpl(PasswordEncoder passwordEncoder, RayUserInfoService<RayUserinfo> rayUserInfoService, RayLoginLogService<RayLoginlog> rayLoginLogService) {
    this.passwordEncoder = passwordEncoder;
    this.rayUserInfoService = rayUserInfoService;
    this.rayLoginLogService = rayLoginLogService;
  }


  @SneakyThrows
  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

    List<RayUserinfo> rayUserInfoList = rayUserInfoService.findByUserOrPwd(s, "");

    RayUserinfo rayUserInfo;

    if (null != rayUserInfoList && !rayUserInfoList.isEmpty()) {

      rayUserInfo = rayUserInfoList.get(0);

      rayLoginLogService.insertOrUpdate(rayUserInfo);
    } else {
      throw new UserNotFindException(String.valueOf(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode()),ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
    }
    return new User(
        s,
        passwordEncoder.encode(rayUserInfo.getPassword()),
        AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
  }
}
