package com.ymd.client.component.activity.mine.config;

import android.os.Bundle;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlterRegPhoneActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_reg_phone);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(R.string.setting_alter_phone);
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
}