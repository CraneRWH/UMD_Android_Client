package com.ymd.client.component.activity.mine.config;

import android.os.Bundle;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改登陆密码
 */
public class AlterLoginPwActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_login_pw);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(R.string.setting_alter_pw);
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
