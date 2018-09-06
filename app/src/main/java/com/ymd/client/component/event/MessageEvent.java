package com.ymd.client.component.event;

import com.ymd.client.component.adapter.merchant.bean.GoodsListBean;

import java.util.List;

/**
 * Created by dalong on 2016/12/27.
 */

public class MessageEvent {
    public int  num;
    public int  price;
    public List<GoodsListBean.DataEntity.GoodscatrgoryEntity.GoodsitemEntity> goods;

    public MessageEvent(int totalNum, int price,List<GoodsListBean.DataEntity.GoodscatrgoryEntity.GoodsitemEntity> goods) {
        this.num = totalNum;
        this.price = price;
        this.goods = goods;
    }
}