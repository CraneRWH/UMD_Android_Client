package com.ymd.client.component.activity.order.u_order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.model.bean.order.UOrderObject;
import com.ymd.client.utils.ToolUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠买单支付成功
 */
public class UOrderPayResultActivity extends BaseActivity {

    @BindView(R.id.rl_main_title)
    RelativeLayout rlMainTitle;
    @BindView(R.id.order_money_tv)
    TextView orderMoneyTv;
    @BindView(R.id.pay_result_tv)
    TextView payResultTv;

    private UOrderObject orderObject;

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context, UOrderObject orderObject) {
        Intent intent = new Intent(context, UOrderPayResultActivity.class);
        intent.putExtra("order", orderObject);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uorder_pay_result);
        ButterKnife.bind(this);

        orderMoneyTv.setText(ToolUtil.changeString(orderObject.getCode()));
    }
}
