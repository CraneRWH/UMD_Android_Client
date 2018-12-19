package com.ymd.client.component.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.utils.AbDateUtil;
import com.ymd.client.utils.StatusBarUtils;
import com.ymd.client.utils.ToastUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开通会员页面
 */
public class OpenMemberActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;//标题

    @BindView(R.id.open_member_month)
    View mViewMonth;//月费
    @BindView(R.id.open_member_season)
    View mViewSeason;//季费
    @BindView(R.id.open_member_year)
    View mViewYear;//年费

    @BindView(R.id.open_member_money)
    TextView mOpenMoney;//支付金额
    @BindView(R.id.open_member_validity_time)
    TextView mOpenValidity;//有效期

    int checked = -1;//选中的卡类别

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_member);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_open_member));

        //默认选中月卡
        checked = 1;
        mViewMonth.setBackgroundResource(R.drawable.open_member_checked_bg);
        mOpenMoney.setText("￥9.9");
        //TODO 月份天数计算
        mOpenValidity.setText("有效期：" + AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMD1) + " - "
                + AbDateUtil.getCurrentDateByOffset(AbDateUtil.dateFormatYMD1,Calendar.DATE, 30));
    }

    @OnClick(R.id.base_back)
    void back() {
        finish();
    }

    @Override
    protected void setStatusBar(int resourcesId) {
        super.setStatusBar(resourcesId);
        StatusBarUtils.StatusBarLightMode(this, true);
    }

    @OnClick({R.id.open_member_month, R.id.open_member_season, R.id.open_member_year})
    void chooseType(View view) {
        switch (view.getId()) {
            case R.id.open_member_month:
                mViewMonth.setBackgroundResource(R.drawable.open_member_checked_bg);
                mViewSeason.setBackgroundResource(R.drawable.open_member_bg);
                mViewYear.setBackgroundResource(R.drawable.open_member_bg);

                checked = 1;

                mOpenMoney.setText("￥9.9");

                //TODO 月份天数计算
                mOpenValidity.setText("有效期：" + AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMD1) + " - "
                        + AbDateUtil.getCurrentDateByOffset(AbDateUtil.dateFormatYMD1,Calendar.DATE, 30));
                break;
            case R.id.open_member_season:
                mViewMonth.setBackgroundResource(R.drawable.open_member_bg);
                mViewSeason.setBackgroundResource(R.drawable.open_member_checked_bg);
                mViewYear.setBackgroundResource(R.drawable.open_member_bg);

                checked = 2;
                mOpenMoney.setText("￥60");
                //TODO 月份天数计算
                mOpenValidity.setText("有效期：" + AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMD1) + " - "
                        + AbDateUtil.getCurrentDateByOffset(AbDateUtil.dateFormatYMD1,Calendar.DATE, 90));
                break;
            case R.id.open_member_year:
                mViewMonth.setBackgroundResource(R.drawable.open_member_bg);
                mViewSeason.setBackgroundResource(R.drawable.open_member_bg);
                mViewYear.setBackgroundResource(R.drawable.open_member_checked_bg);

                checked = 3;
                mOpenMoney.setText("￥99");
                //TODO 月份天数计算
                mOpenValidity.setText("有效期：" + AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMD1) + " - "
                        + AbDateUtil.getCurrentDateByOffset(AbDateUtil.dateFormatYMD1,Calendar.DATE, 365));
                break;
        }
    }

    @OnClick(R.id.open_member_submit)
    void submit(View view) {
        //支付
        ToastUtil.ToastMessage(this, "支付");
    }

    @OnClick(R.id.open_member_agreement)
    void clickAgree(View view){
        ToastUtil.ToastMessage(this, "查看服务协议");
    }
}
