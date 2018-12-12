package com.ymd.client.component.event;

import com.ymd.client.model.bean.homePage.YmdGoodsEntity;

import java.util.List;

/**
 * 作者:rongweihe
 * 日期:2018/12/12  时间:21:50
 * 描述:
 * 修改历史:
 */
public class ClearShopCarEvent {
    private List<YmdGoodsEntity> goods;
    private boolean isClear;

    public ClearShopCarEvent(boolean isClear,List<YmdGoodsEntity> goods) {
        this.goods = goods;
        this.isClear = isClear;

        for (YmdGoodsEntity item : this.goods) {
            item.setBuyCount(0);
        }
    }

    public List<YmdGoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<YmdGoodsEntity> goods) {
        this.goods = goods;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }
}
