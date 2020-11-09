package com.tan.play.sms.ray.utils;

import com.tan.play.sms.ray.entity.WeekInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/** @Author 谭婧杰 */
@Slf4j
public class DateUtil {

  /**
   * 得到当前时间
   *
   * @return
   */
  public static String getNowTimes3() {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
    String tm = fmt.format(new Date());
    return tm;
  }

  /**
   * 获取14位时时间戳
   *
   * @return String yyyymmddhhmmss
   */
  public static String generateTimeStamp() {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    return format.format(new Date());
  }

  /**
   * 获取八位时时间戳
   *
   * @return String yyyymmdd
   */
  public static String generate8TimeStamp() {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    return format.format(new Date());
  }

  public static String dateToStringPattern(String str, String pattern1, String pattern2) {
    String DateEnd = "";
    try {

      Date curDateEnd = new SimpleDateFormat(pattern1).parse(str);
      DateEnd = new SimpleDateFormat(pattern2).format(curDateEnd);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return DateEnd;
  }

  /**
   * 获取当前时间
   *
   * @return new date
   */
  public static Date getDate() {
    return new Date(System.currentTimeMillis());
  }

  /**
   * yyyyMMddHHmmss 转 yyyy-MM-dd HH:mm:ss
   *
   * @param date String yyyyMMddHHmmss
   * @return String yyyy-MM-dd HH:mm:ss
   */
  public static String strToDateFormat(String date) {
    if (StringUtils.isEmpty(date)) {
      return "";
    }
    try {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
      LocalDateTime ldt = LocalDateTime.parse(date, dtf);
      DateTimeFormatter fa = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      return ldt.format(fa);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * yyyyMMdd 转  yyyy-MM-dd
   * @param date yyyyMMdd
   * @return String yyyy-MM-dd
   * @throws ParseException  ParseException
   */
  public static String strToDateFormatYY(String date)  {
    Date format1 = null;
    try{
      format1 = new SimpleDateFormat("yyyyMMdd").parse(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new SimpleDateFormat("yyyy-MM-dd").format(format1);
  }

  public static Date strToDatePattern(String str, String pattern) throws RuntimeException {
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    Date result = null;
    try {
      if (!StringUtils.isEmpty(str)) {
        result = dateFormat.parse(str);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 返回为相差月份
   *
   * @param dateMonth1  月份
   * @param dateMonth2 月份
   * @return 差
   */
  public static Integer getMonthSpace(String dateMonth1, String dateMonth2) {

    Temporal temporal1 = LocalDate.parse(dateMonth1);
    Temporal temporal2 = LocalDate.parse(dateMonth2);
    long l = ChronoUnit.MONTHS.between(temporal1, temporal2);
    System.out.println(l);
    return (int) l;
  }

  /**
   * 日期加n个月
   *
   * @param date date
   * @return String
   * @throws ParseException
   */
  public static String subMonth(String date, Integer amount) throws ParseException {

    Date format1;
    format1 = new SimpleDateFormat("yyyyMMdd").parse(date);
    String longDate = new SimpleDateFormat("yyyy-MM-dd").format(format1);
    System.out.println("yyyyMMdd转yyyy-MM-dd:" + longDate);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dt = sdf.parse(longDate);
    Calendar rightNow = Calendar.getInstance();
    rightNow.setTime(dt);
    rightNow.add(Calendar.MONTH, amount);
    Date dt1 = rightNow.getTime();
    return sdf.format(dt1);
  }


  /**
   * 获取本月周次和日期时间段信息
   * @return
   */
  public static List<WeekInfoModel> getThisMonthWeekDate(Date start , Date end ) {
    List<WeekInfoModel> list = new ArrayList<WeekInfoModel>();
    // 第一天
    int i = 0 ;
    while(end.getTime()-start.getTime()>0){
      Date sDate = null ;
      Date eDate = null ;
      Calendar firstDayCal = Calendar.getInstance();
      firstDayCal.setTime(start);
      int weekDay = firstDayCal.get(Calendar.DAY_OF_WEEK) - 1;
      // 周一
      if (weekDay == 1) {
        sDate = start;
      } else {
        firstDayCal.add(Calendar.DATE, -weekDay);
        sDate = firstDayCal.getTime();
      }
      firstDayCal.add(Calendar.DATE, 6);
      eDate = firstDayCal.getTime();


      WeekInfoModel model = new WeekInfoModel();
      model.setIndexOfWeek(i + 1);
      model.setStartDateOfWeek(formatDate(sDate,"yyyyMMdd"));
      model.setEndDateOfWeek(formatDate(eDate,"yyyyMMdd"));
      list.add(model);

      firstDayCal.add(Calendar.DATE ,1) ;
      start =firstDayCal.getTime();
      i ++ ;
    }
    return list;
  }

  /**
   * 日期转string
   * @param  value value
   * @param pattern pattern
   * @return String
   */
  public static String formatDate(Date value, String pattern)
  {
    String pat = "yyyy-MM-dd";
    if (pattern != null) {
      pat = pattern;
    }
    String result = "";
    if (value != null) {
      SimpleDateFormat htmlDf = new SimpleDateFormat(pat);
      result = htmlDf.format(value).toString();
    }
    return result;
  }


  public static Date  getLatestDay(Date current , int days) {
    Calendar c = Calendar.getInstance();
    c.setTime(current);
    c.add(5, days);
    return c.getTime();
  }

}
