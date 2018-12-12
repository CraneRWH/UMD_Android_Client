package com.ymd.client.component.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.component.adapter.merchant.ShopCarAdapter;
import com.ymd.client.component.event.ClearShopCarEvent;
import com.ymd.client.component.event.GoodsEvent;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.utils.ToolUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:rongweihe
 * 日期:2018/12/9  时间:11:32
 * 描述:
 * 修改历史:
 */
public class ShopCarDialog extends Dialog {
    private TextView warnNumTv;
    private TextView orderMoneyTv;
    private TextView productMoneyTv;
    private TextView disTv;
    private TextView noShop;
    private TextView submitBtn;
    private LinearLayout footView;
    private RelativeLayout shopCartMain;
    private ImageView clearIv;

    public ShopCarDialog(@NonNull Context context) {
        super(context);
    }

    private View contentView;
    private Activity activity;

    private ResultListener listener;

    private RecyclerView shopCarRv;
    private View outSideView;
    private List<YmdGoodsEntity> list = new ArrayList<>();
    private int flag = -1;

    ShopCarAdapter shopCaradapter;

    public ShopCarDialog(Activity context, ResultListener listener) {
        super(context, R.style.myAlertDialog);
        this.activity = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(activity);
        contentView = inflater.inflate(R.layout.dialog_shop_car, null);
        this.setContentView(contentView);

        bindComponent();
        bindInfoAndListener();
    }

    protected void bindComponent() {
        shopCarRv = (RecyclerView) contentView.findViewById(R.id.listView);
        outSideView = (View) contentView.findViewById(R.id.outSide);
        warnNumTv = (TextView) contentView.findViewById(R.id.warn_num_tv);
        orderMoneyTv = (TextView) contentView.findViewById(R.id.order_money_tv);
        productMoneyTv = (TextView) contentView.findViewById(R.id.product_money_tv);
        disTv = (TextView) contentView.findViewById(R.id.dis_tv);
        noShop = (TextView) contentView.findViewById(R.id.noShop);
        submitBtn = (TextView) contentView.findViewById(R.id.submit_btn);
        footView = (LinearLayout) contentView.findViewById(R.id.foot_view);
        clearIv = (ImageView) contentView.findViewById(R.id.empty_shopping_car);
    }

    protected void bindInfoAndListener() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        shopCarRv.setLayoutManager(layoutManager);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onResult(1);
                }
            }
        });
        clearIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ClearShopCarEvent(true, list));
                list.clear();
                refreshData();
                dismiss();
            }
        });
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

        shopCaradapter = new ShopCarAdapter(list);
        shopCarRv.setAdapter(shopCaradapter);
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
   /*     Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);*/
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }

    private List<YmdGoodsEntity> buyList = new ArrayList<>();
    public void refreshGoodsView(GoodsEvent goodsEntity) {
        try {
            double allMoney = 0;
            double disAllMoney = 0;
            buyList.clear();
            int count = 0;
            for (YmdGoodsEntity item : goodsEntity.getGoods()) {
                allMoney = ToolUtil.changeDouble(item.getPrice()) * item.getBuyCount() + allMoney;
                count = count + item.getBuyCount();
                //    disAllMoney = ((ToolUtil.changeDouble(item.getPrice()) * ToolUtil.changeDouble(merchantInfo.getDiscount())) / 10) * item.getBuyCount() + disAllMoney;
                disAllMoney = ToolUtil.changeDouble(item.getPreferentialPrice()) * item.getBuyCount() + disAllMoney;
            }
            goodsEntity.setAllMoney(allMoney);
            goodsEntity.setDisAllMoney(disAllMoney);
            productMoneyTv.setText(ToolUtil.double2Point(allMoney) + "元");
            orderMoneyTv.setText("¥" + ToolUtil.double2Point(disAllMoney));
            if (goodsEntity.getGoods() == null || goodsEntity.getGoods().isEmpty()) {
                noShop.setVisibility(View.GONE);
                warnNumTv.setVisibility(View.GONE);
                warnNumTv.setText("0");
            } else {
                warnNumTv.setText(ToolUtil.changeString(count));
                warnNumTv.setVisibility(View.VISIBLE);
                noShop.setVisibility(View.GONE);
                buyList.addAll(goodsEntity.getGoods());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public interface ResultListener {
        public void onResult(int position);
    }
}
