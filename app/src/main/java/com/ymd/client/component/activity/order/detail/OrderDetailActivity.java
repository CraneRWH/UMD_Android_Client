package com.ymd.client.component.activity.order.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.order.OrderPageFragment;
import com.ymd.client.component.activity.order.pay.OrderPayActivity;
import com.ymd.client.component.adapter.AppFragmentPageAdapter;
import com.ymd.client.component.event.OrderEvent;
import com.ymd.client.component.widget.other.MyChooseItemView;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.model.bean.order.OrderResultForm;
import com.ymd.client.model.bean.order.YmdOrderGoods;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 作者:rongweihe
 * 日期:2018/8/25
 * 描述:    “订单详情界面”
 * 修改历史:
 */
public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.chooseItem0)
    MyChooseItemView chooseItem0;
    @BindView(R.id.chooseItem1)
    MyChooseItemView chooseItem1;
    @BindView(R.id.chooseItem2)
    MyChooseItemView chooseItem2;
    @BindView(R.id.businessView)
    LinearLayout businessView;
    @BindView(R.id.businessViewPager)
    ViewPager businessViewPager;


    public int chooseStatus = 0;
    protected int status;
    @BindView(R.id.warn_num_tv)
    TextView warnNumTv;
    @BindView(R.id.order_money_tv)
    TextView orderMoneyTv;
    @BindView(R.id.product_money_tv)
    TextView productMoneyTv;
    @BindView(R.id.dis_tv)
    TextView disTv;
    @BindView(R.id.submit_btn)
    TextView submitBtn;

    private List<Fragment> fragmentList;
    private List<MyChooseItemView> textViewList;

    private long orderId;
    private int functionType;    //美食相关类别的标记
    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context,long orderId,int functionType) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("functionType", functionType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("订单");

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            orderId = bundle.getLong("orderId");
            functionType = bundle.getInt("functionType");
            if (functionType == 0) {
                functionType = 1;
            }
            requestOrderDetail();
        }
        status = 2;


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderEvent event = new OrderEvent();
                event.setType(businessViewPager.getCurrentItem());
                event.setOrderDetail(orderDetail);
                EventBus.getDefault().post(event);
            }
        });
    }


    private void resetOrderView() {
        orderMoneyTv.setText(ToolUtil.changeString(orderDetail.getTotalAmt()) + "元");
        productMoneyTv.setText(ToolUtil.changeString(orderDetail.getPayAmt()) + "元");
        disTv.setText("优惠金额"+ToolUtil.changeString(orderDetail.getDiscountAmt())+ "元");
        int count = 0;
        for (YmdOrderGoods item : orderDetail.getYmdOrderGoodsList()) {
            count = count + item.getGoodsNum();
        }
        warnNumTv.setText(ToolUtil.changeString(count));
        if (ToolUtil.changeInteger(orderDetail.getOrderStatus()) > 0) {
            submitBtn.setVisibility(View.GONE);
        } else {
            submitBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 选择显示第几个选项卡
     *
     * @param position
     */
    protected void chooseItem(int position) {
        chooseStatus = position;
/*        Bundle bundle = new Bundle();
        bundle.putInt("chooseStatus", chooseStatus);
        fragmentList.get(chooseStatus).setArguments(bundle);*/
        try {
            for (int i = 0; i < textViewList.size(); i++) {
                if (i == position) {
                    textViewList.get(i).setChoose(true);
                } else {
                    textViewList.get(i).setChoose(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    protected void viewPagerListener() {
        for (int i = 0; i < textViewList.size(); i++) {
            final int position = i;
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    businessViewPager.setCurrentItem(position);
                    chooseItem(position);
                }
            });
        }
        businessViewPager.setAdapter(new AppFragmentPageAdapter(getSupportFragmentManager(), fragmentList));
        businessViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Do Nothing
            }

            @Override
            public void onPageSelected(int position) {
                chooseItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Do Nothing
            }
        });
    }

    private void requestOrderDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        WebUtil.getInstance().requestPOST(this, URLConstant.ORDER_DETAIL, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        resetDetailFragment(result.optString("ymdOrder"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }
    OrderResultForm orderDetail;
    private void resetDetailFragment(String resultJson) {
        orderDetail = new Gson().fromJson(resultJson, OrderResultForm.class);
        fragmentList = new ArrayList<Fragment>();
        textViewList = new ArrayList<MyChooseItemView>();
        if (functionType == 1) {
            fragmentList.add(OrderDetailFragment.newInstance(0, functionType,orderDetail));
            fragmentList.add(OrderDetailFragment.newInstance(1, functionType,orderDetail));

            textViewList.add(chooseItem0);
            textViewList.add(chooseItem1);
        } else {
            businessView.setVisibility(View.GONE);
            fragmentList.add(OrderDetailFragment.newInstance(0, functionType,orderDetail));
        }
        //    fragmentList.add(new OrderDetailFragment());

        viewPagerListener();
        chooseItem(0);

        resetOrderView();
    }
}
