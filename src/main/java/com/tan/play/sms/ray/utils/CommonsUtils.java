package com.tan.play.sms.ray.utils;

import com.tan.play.sms.ray.exception.CommonEnum;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

/** @Author 谭婧杰 */
public class CommonsUtils {
  /**
   * 获取v
   * @param code 键
   * @return 值
   */
  public static String getValue(String code) {
    for (CommonEnum commonEnum : CommonEnum.values()) {
      if (commonEnum.getResultCode().equals(code)) {
        return commonEnum.getResultMsg();
      }
    }
    return null;
  }

  /**
   * MD5加密
   *
   * @param s s
   * @return String
   */
  public static String getMD5(String s) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] bytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
      return toHex(bytes);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String toHex(byte[] bytes) {
    final char[] hexDigits = "0123456789abcdef".toCharArray();
    StringBuilder ret = new StringBuilder(bytes.length * 2);
    for (byte aByte : bytes) {
      ret.append(hexDigits[(aByte >> 4) & 0x0f]);
      ret.append(hexDigits[aByte & 0x0f]);
    }
    return ret.toString();
  }

  public static String generateCode() {
    // 定义变长字符dao串
    StringBuilder str = new StringBuilder();
    int l = 8;
    Random random = new Random();
    // 随机生成数字，zhi并添加到字符串
    for (int i = 0; i < l; i++) {
      str.append(random.nextInt(10));
    }

    return str.toString();
  }
}
