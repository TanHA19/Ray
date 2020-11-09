package com.tan.play.sms.ray.entity.commons.emun;

/**
 * @Author 谭婧杰 订单
 **/

public enum OrderCode {
    /**
     * 订单状态
     */
    ORDER_NORMAL("01", "正常"),
    ORDER_PAST_DUE("02", "取消"),
    ORDER_NO_PAY("03","未支付"),
    /**
     * 订单类型
     */
    ORDER_TYPE("01","短信充值")
    ;

    OrderCode(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String status;
    private String message;
}
