package com.ymd.client.component.event;

import com.ymd.client.model.bean.homePage.YmdGoodsEntity;

import java.util.List;

/**
 * 包名:com.ymd.client.component.event
 * 类名:
 * 时间:2018/9/12 0012Time:16:55
 * 作者:荣维鹤
 * 功能简介:    计算购物车价格
 * 修改历史:
 */
public class GoodsEvent {

    private List<YmdGoodsEntity> goods; //商品
    private double allMoney;    //总价格
    private double discount;    //打折率
    private double disAllMoney; //打折后的总价格

    public double getDisAllMoney() {
        return disAllMoney;
    }

    public void setDisAllMoney(double disAllMoney) {
        this.disAllMoney = disAllMoney;
    }

    public List<YmdGoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<YmdGoodsEntity> goods) {
        this.goods = goods;
    }

    public double getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(double allMoney) {
        this.allMoney = allMoney;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
