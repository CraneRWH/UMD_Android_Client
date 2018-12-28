package com.ymd.client.component.activity.mine.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.utils.ToolUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberPayResultActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;//标题

    @BindView(R.id.order_money_tv)
    TextView orderMoneyTv;
    @BindView(R.id.pay_result_tv)
    TextView payResultTv;

    private String orderNo;
    private String money;

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context, String orderNo,String money) {
        Intent intent = new Intent(context, MemberPayResultActivity.class);
        intent.putExtra("order", orderNo);
        intent.putExtra("money", money);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_pay_result);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);

        mTxtTitle.setText("支付结果");
        initView();
    }

    private void initView(){
        if (getIntent()!= null) {
            money = getIntent().getExtras().getString("money");
            setShopData();
        } else {
            finish();
        }
    }

    private void setShopData() {
        orderMoneyTv.setText(ToolUtil.changeString(money));
    }

    @OnClick(R.id.member_pay_back)
    void back(){
        finish();
    }
}
