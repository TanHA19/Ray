package com.tan.play.sms.ray.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 基于 httpclient 4.5版本的 http工具类
 *
 * @author 谭婧杰
 */
public class HttpClientTool {


  public static String doPostSms(String httpUrl, Map<String, Object> param) {
    try {
      // 创建连接
      URL url = new URL(httpUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setRequestMethod("POST");
      connection.setUseCaches(false);
      connection.setInstanceFollowRedirects(true);
      connection.connect();
      // POST请求
      DataOutputStream out = new DataOutputStream(connection.getOutputStream());
      out.write(getDataStr(param).getBytes());
      out.flush();
      out.close();
      // 读取响应
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String lines;
      StringBuilder strBuffer = new StringBuilder();
      while ((lines = reader.readLine()) != null) {
        lines = new String(lines.getBytes(), StandardCharsets.UTF_8);
        strBuffer.append(lines);
      }
      reader.close();
      connection.disconnect();
      return strBuffer.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  /**
   * 拼接post参数
   *
   * @param map map
   * @return String
   * @throws UnsupportedEncodingException 不支持编码异常
   */
  private static String getDataStr(Map<String, Object> map) throws UnsupportedEncodingException {
    StringBuilder params = new StringBuilder();
    for (Map.Entry<String, Object> data : map.entrySet()) {
      if (null != data.getKey()
          && !"".equals(data.getKey())
          && null != data.getValue()
          && !"".equals(data.getValue())) {
        if ("ms".equals(data.getKey())) {
          params
              .append("&")
              .append(data.getKey())
              .append("=")
              .append(URLEncoder.encode((String) data.getValue(), "utf-8"));
        } else {
          params.append("&").append(data.getKey()).append("=").append(data.getValue());
        }
      }
    }
    return params.toString().substring(1, params.length());
  }
}
