package com.ymd.client.component.activity.homePage.food.seller;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ymd.client.R;
import com.ymd.client.component.adapter.MySimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 包名:com.ymd.client.component.activity.homePage.food.seller
 * 类名:
 * 时间:2018/9/11 0011Time:17:18
 * 作者:荣维鹤
 * 功能简介:
 * 修改历史:
 */
public class ShopCarPopupWindow extends PopupWindow {

    private View contentView;
    private Activity activity;

    private ResultListener listener;

    private ListView listView;
    private View outSideView;
    private ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    private int flag = -1;
    public ShopCarPopupWindow(Activity context,String[] listStr,ResultListener listener) {
        this.activity = context;
        for (String item : listStr) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("item", item);
            list.add(map);
        }
        onCreate();
        this.listener = listener;
    }

    private void onCreate() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.item_choose_popupwindow, null);
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
        listView = (ListView) contentView.findViewById(R.id.listView);
        outSideView = (View) contentView.findViewById(R.id.outSide);
    }

    protected void bindInfoAndListener() {
        outSideView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });
        MySimpleAdapter adapter = new MySimpleAdapter(activity, list, R.layout.item_choose_popupwindow_item,
                new String[]{"item"}, new int[]{R.id.popup_list_item1}, new MySimpleAdapter.MyViewListener() {

            @Override
            public void callBackViewListener(Map<String, Object> data, View view, ViewGroup parent, int position) {
                if (position == flag) {
                    view.findViewById(R.id.popup_list_item2).setVisibility(View.VISIBLE);
                }
                else {
                    view.findViewById(R.id.popup_list_item2).setVisibility(View.INVISIBLE);
                }
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (position != flag) {
                    flag = position;
                    ((MySimpleAdapter)listView.getAdapter()).notifyDataSetChanged();
                }
                if (listener != null) {
                    listener.onResult(position);
                }
                dismiss();
            }
        });
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }

    public interface ResultListener {
        public void onResult(int position);
    }

}
