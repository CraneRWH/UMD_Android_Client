package com.ymd.client.component.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-设置
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(R.string.setting_title);
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

    @OnClick({R.id.setting_alter_pw, R.id.setting_alter_ges_pw, R.id.setting_alter_phone})
    void click(View view) {
        switch (view.getId()) {
            case R.id.setting_alter_pw:
                //修改登陆密码
                break;
            case R.id.setting_alter_ges_pw:
                //修改手势密码
                break;
            case R.id.setting_alter_phone:
                //修改注册手机号
                break;
        }

    }
}
