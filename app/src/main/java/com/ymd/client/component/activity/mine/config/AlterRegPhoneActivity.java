package com.ymd.client.component.activity.mine.config;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改注册手机号
 */
public class AlterRegPhoneActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @BindView(R.id.alter_phone_num)
    EditText mEtPhone;
    @BindView(R.id.alter_phone_sms_code)
    EditText mEtSmsCode;
    @BindView(R.id.alter_phone_pw)
    EditText mEtPw;

    @BindView(R.id.alter_phone_get_code)
    TextView mTxtGetCode;

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
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @OnClick(R.id.alter_phone_submit)
    void submit(){
        String phone = mEtPhone.getText().toString().trim();

    }
}
