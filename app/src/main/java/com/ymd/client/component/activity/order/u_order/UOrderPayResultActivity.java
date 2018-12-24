package com.ymd.client.component.activity.order.u_order;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.order.pay.OrderPayResultActivity;
import com.ymd.client.model.bean.order.OrderResultForm;

/**
 *  优惠买单支付成功
 */
public class UOrderPayResultActivity extends BaseActivity {

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context, OrderResultForm orderResultForm) {
        Intent intent = new Intent(context, UOrderPayResultActivity.class);
        intent.putExtra("order", orderResultForm);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uorder_pay_result);
    }
}
