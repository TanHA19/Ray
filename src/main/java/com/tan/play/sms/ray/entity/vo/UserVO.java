package com.tan.play.sms.ray.entity.vo;

import com.tan.play.sms.ray.entity.RayAccountAssets;
import com.tan.play.sms.ray.entity.RayLoginlog;
import com.tan.play.sms.ray.entity.RayUserExtend;
import com.tan.play.sms.ray.entity.RayUserinfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/** @Author tan */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {
  private RayUserExtend rayUserExtend;
  private RayUserinfo rayUserInfo;
  private RayAccountAssets rayAccountAssets;
  private RayLoginlog rayLoginlog;
}
