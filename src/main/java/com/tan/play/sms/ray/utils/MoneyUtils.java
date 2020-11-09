package com.tan.play.sms.ray.utils;

import java.text.DecimalFormat;

/**
 * 金额格式化工具类
 *
 * @author 谭婧杰
 */
public class MoneyUtils {

  /**
   * 将数字转换成指定格式(##,###.000)的金额形式。
   *
   * @param money money
   * @return String
   */
  public static String format(double money) {
    return format(money, "##,###.000");
  }

  /**
   * 将数字转换成指定格式的金额形式。 例：format(1234.456,"##,###.000") 结果 1,234.456
   *
   * @param money money
   * @param format String
   * @return
   */
  public static String format(double money, String format) {
    DecimalFormat moneyFormat = new DecimalFormat();
    moneyFormat.applyPattern(format);
    return moneyFormat.format(money);
  }



  /** 分转金额(无小数) */
  public static double parseAmtValue(Integer num) {
    if (num == null) {
      return 0.0;
    } else {
      return num / 100.0;
    }
  }
}
