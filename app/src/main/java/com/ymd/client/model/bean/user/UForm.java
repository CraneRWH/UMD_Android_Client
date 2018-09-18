package com.ymd.client.model.bean.user;

import java.util.List;

public class UForm {
    private Long id;
    //订单编号
    private String orderNumber;
    //u币数量
    private Integer number;
    //剩余U币
    private Integer available;
    //商品集合  名称  、数量
    private List<GoodsForm> goods;
    //总价
    private String payAmt;
    //时间
    private String time ;
    //商户ID
    private Long merchantId;
    //商户头像
    private String icon;
    //商户名称
    private String merchantName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public List<GoodsForm> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsForm> goods) {
        this.goods = goods;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
