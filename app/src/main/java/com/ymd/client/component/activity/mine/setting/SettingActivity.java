package com.ymd.client.component.activity.mine.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.mine.setting.config.AlterGesActivity;
import com.ymd.client.component.activity.mine.setting.config.AlterLoginPwActivity;
import com.ymd.client.component.activity.mine.setting.config.AlterRegPhoneActivity;
import com.ymd.client.component.activity.mine.setting.config.SetGesActivity;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.ACache;
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
    @BindView(R.id.setting_ges_txt)
    TextView mTxtGes;
    ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(R.string.setting_title);
        aCache = ACache.get(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    private void init() {
        byte[] gpw = aCache.getAsBinary(Constants.GES_KEY);
        if (!TextUtils.isEmpty(Constants.GES_KEY) && gpw != null && gpw.length != 0) {
            mTxtGes.setText(getString(R.string.setting_alter_ges_pw));
        } else {
            mTxtGes.setText(getString(R.string.setting_ges_pw));
        }
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
                startActivity(new Intent(this, AlterLoginPwActivity.class));
                break;
            case R.id.setting_alter_ges_pw:
                //修改手势密码
                byte[] gpw = aCache.getAsBinary(Constants.GES_KEY);
                if (!TextUtils.isEmpty(Constants.GES_KEY) && gpw != null && gpw.length != 0) {
                    //修改手势密码
                    startActivity(new Intent(this, AlterGesActivity.class));
                } else {
                    //设置手势密码
                    startActivity(new Intent(this, SetGesActivity.class));
                }
                break;
            case R.id.setting_alter_phone:
                //修改注册手机号
                startActivity(new Intent(this, AlterRegPhoneActivity.class));
                break;
        }

    }
}
