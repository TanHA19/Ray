package com.tan.play.sms.ray.entity.commons.emun;

/**
 * @Author 谭婧杰
 **/

public enum  UserCode {

    /**
     * 用户状态
     */
    USER_NORMAL(0, "正常"),
    USER_PAST_DUE(1, "未充值,或者余额不足"),
    USER_BLACK_LIST(2, "黑名单用户"),
    /**
     * 用户登录方式
     */
    USER_PWD_LOGIN(0,"密码登录");
    private Integer status;

    UserCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
