package com.ymd.client.component.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ymd.client.R;
import com.ymd.client.component.adapter.merchant.ShopCarAdapter;
import com.ymd.client.component.event.GoodsEvent;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.utils.ToolUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:rongweihe
 * 日期:2018/12/9  时间:11:32
 * 描述:
 * 修改历史:
 */
public class ShopCarDialog extends Dialog {
    public ShopCarDialog(@NonNull Context context) {
        super(context);
    }

    private View contentView;
    private Activity activity;

    private ShopCarDialog.ResultListener listener;

    private RecyclerView shopCarRv;
    private View outSideView;
    private List<YmdGoodsEntity> list = new ArrayList<>();
    private int flag = -1;
    public ShopCarDialog(Activity context,ResultListener listener) {
        super(context, R.style.myAlertDialog);
        this.activity = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(activity);
        contentView = inflater.inflate(R.layout.popupwindow_shop_car, null);
        this.setContentView(contentView);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = activity.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
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

    public List<YmdGoodsEntity> getList() {
        return list;
    }

    public void setList(List<YmdGoodsEntity> list) {
        this.list = list;
        refreshData();
    }

    private void refreshData() {
        GoodsEvent goodsEvent = new GoodsEvent();
        goodsEvent.setGoods(list);
        EventBus.getDefault().post(goodsEvent);

        ShopCarAdapter adapter = new ShopCarAdapter(list);
        shopCarRv.setAdapter(adapter);
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
        if (flag && entity.getBuyCount() > 0) {
            entity.setBuyCount(1);
            list.add(entity);
        }
        refreshData();
    }

    @Override
    public void show() {
 /*       Window win = this.getWindow();
        DisplayMetrics dm = new DisplayMetrics();
        int heigth = dm.heightPixels;
        int width = dm.widthPixels;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.x = 0;//设置x坐标
        params.y = heigth - ((int) activity.getResources().getDimension(R.dimen.mar_pad_len_105px));//设置y坐标
        win.setAttributes(params);
*/
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        super.show();
    }

    public interface ResultListener {
        public void onResult(int position);
    }
}
