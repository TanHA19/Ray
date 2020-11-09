package com.tan.play.sms.ray.entity.commons;

/**
 * @Author tan
 **/
public interface Constant {
    /**
     * 日期分割符
     */
    String REPLACE_CONSTANT = "-";
    /**
     * 发送短信最大值
     */
    Integer MAX_TEXT_LENGTH = 1000;

    /**
     * 登出成功页面
     */
    String LOGOUT_URL = "/ray_signIn";

    /**
     * 默认登录成功页面
     *
     */
    String LOGIN_URL = "/toPage";

    String LOGIN_URL1 = "/raySignIn";

    /**
     *  响应编码
     *
     */
    String ENCODING_TYPE ="application/json;charset=UTF-8";
}
