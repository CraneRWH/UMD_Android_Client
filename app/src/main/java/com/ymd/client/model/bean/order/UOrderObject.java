package com.ymd.client.model.bean.order;

import java.io.Serializable;

/**
 * 作者:rongweihe
 * 日期:2018/12/26  时间:20:38
 * 描述:
 * 修改历史:
 */
public class UOrderObject implements Serializable {
    private double uNumber;
    private long id;
    private double discountMoney;

    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getuNumber() {
        return uNumber;
    }

    public void setuNumber(double uNumber) {
        this.uNumber = uNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(double discountMoney) {
        this.discountMoney = discountMoney;
    }
}
