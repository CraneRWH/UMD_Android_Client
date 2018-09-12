package com.ymd.client.component.activity.homePage.food.seller;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ymd.client.R;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.component.adapter.merchant.ShopCarAdapter;
import com.ymd.client.component.event.GoodsEvent;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.utils.ToolUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.ymd.client.component.activity.homePage.food.seller
 * 类名:
 * 时间:2018/9/11 0011Time:17:18
 * 作者:荣维鹤
 * 功能简介:    购物车
 * 修改历史:
 */
public class ShopCarPopupWindow extends PopupWindow {

    private View contentView;
    private Activity activity;

    private ResultListener listener;

    private RecyclerView shopCarRv;
    private View outSideView;
    private List<YmdGoodsEntity> list = new ArrayList<>();
    private int flag = -1;
    public ShopCarPopupWindow(Activity context,ResultListener listener) {
        this.activity = context;
        onCreate();
        this.listener = listener;

    }

    private void onCreate() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popupwindow_shop_car, null);
        this.setContentView(contentView);

        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);

        this.update();
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(R.color.bg_color));
        //点back键和其他地方使其小时，设置了这个才能出发OnDismissListener,设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        //设置弹出船体动画效果
        this.setAnimationStyle(android.R.style.Animation_Dialog);
        bindComponent();
        bindInfoAndListener();
    }

    protected void bindComponent() {
        shopCarRv = (RecyclerView) contentView.findViewById(R.id.listView);
        outSideView = (View) contentView.findViewById(R.id.outSide);
    }

    protected void bindInfoAndListener() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        shopCarRv.setLayoutManager(layoutManager);

        refreshData();
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }

    public List<YmdGoodsEntity> getList() {
        return list;
    }

    public void setList(List<YmdGoodsEntity> list) {
        this.list = list;
        refreshData();
    }

    private void refreshData() {

        ShopCarAdapter adapter = new ShopCarAdapter(list);
        shopCarRv.setAdapter(adapter);

        GoodsEvent goodsEvent = new GoodsEvent();
        goodsEvent.setGoods(list);
        EventBus.getDefault().post(goodsEvent);
    }

    public void addGood(YmdGoodsEntity entity) {
        boolean flag = true;
        for (YmdGoodsEntity item : list) {
            if (item.getId() == entity.getId()) {
                item.setBuyCount(ToolUtil.changeInteger(entity.getBuyCount()));
                if (item.getBuyCount() == 0) {
                    list.remove(item);
                }
                flag = false;
                break;
            }
        }
        if (flag) {
            entity.setBuyCount(1);
            list.add(entity);
        }
        refreshData();
    }



    public interface ResultListener {
        public void onResult(int position);
    }

}
