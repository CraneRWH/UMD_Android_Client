package com.ymd.client.model.constant;

/**
 * 数据常量类
 */
public class UmdDataConstants {

    public static final String[] orderStatusList = new String[]{
        "待支付",
        "已支付",
        "待消费",
        "商家拒单",
        "待评价",
        "订单已取消",
        "订单已失效",
        "已评价"
    };
    public static final String[] payStatusList = new String[]{
            "创建",
            "待支付",
            "支付成功",
            "支付失败",
            "退款申请",
            "退款处理中",
            "退款成功",
            "退款失败"
    };
}
