package com.ymd.client.component.activity.order.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.order.OrderPageFragment;
import com.ymd.client.component.activity.order.pay.OrderPayActivity;
import com.ymd.client.component.adapter.AppFragmentPageAdapter;
import com.ymd.client.component.widget.other.MyChooseItemView;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
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
        status = 3;
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new OrderDetailFragment());
        fragmentList.add(new OrderDetailFragment());
    //    fragmentList.add(new OrderDetailFragment());

        textViewList = new ArrayList<MyChooseItemView>();
        textViewList.add(chooseItem0);
        textViewList.add(chooseItem1);
    //    textViewList.add(chooseItem2);
        viewPagerListener();
        chooseItem(0);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderPayActivity.startAction(OrderDetailActivity.this);
            }
        });
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
}
