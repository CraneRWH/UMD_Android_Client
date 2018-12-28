package com.ymd.client.component.activity.mine.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.LogUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberPayResultActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView mTxtTitle;//标题

    @BindView(R.id.order_money_tv)
    TextView orderMoneyTv;
    @BindView(R.id.pay_result_tv)
    TextView payResultTv;

    private String orderNo;
    private String money;

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context, String orderNo, String money) {
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

    private void getMemberInfo() {
        WebUtil.getInstance().requestPOST(this, URLConstant.GET_MEMBER_INFO, null, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        JSONObject userStr = result.optJSONObject("user");
                        LoginInfo.setLoginInfo(userStr.toString());
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void initView() {
        if (getIntent() != null) {
            money = getIntent().getExtras().getString("money");
            setShopData();

            getMemberInfo();
        } else {
            finish();
        }
    }

    private void setShopData() {
        orderMoneyTv.setText(ToolUtil.changeString(money));
    }

    @OnClick(R.id.member_pay_back)
    void back() {
        finish();
    }
}
