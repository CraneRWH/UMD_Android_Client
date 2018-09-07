package com.ymd.client.component.activity.mine.setting.config;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.login.RegisterActivity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.StatusBarUtils;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改注册手机号
 */
public class AlterRegPhoneActivity extends BaseActivity {

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
        setTitle(R.string.setting_alter_phone);
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
        if (phone.length() == 0) {
            ToastUtil.ToastMessage(this, "请输入新手机号");
            return;
        }
        String phoneCode = ToolUtil.changeString(mEtSmsCode.getText());
        if (phoneCode.length() == 0) {
            ToastUtil.ToastMessage(this, "请输入验证码");
            return;
        }
  /*      String password = ToolUtil.changeString(mEtPw.getText());
        if (password.length() == 0) {
            ToastUtil.ToastMessage(this, "请输入密码");
            return;
        }*/
        Map<String,Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", phoneCode);
        WebUtil.getInstance().requestPOST(this, URLConstant.CHANGE_LOGIN_PHONE, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        ToastUtil.ToastMessage(getApplicationContext(), "手机号码修改成功");
                        finish();
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                        ToastUtil.ToastMessage(getApplicationContext(), "手机号码修改失败");
                    }
                });
    }

    private TimeTask timeTask;
    @OnClick(R.id.alter_phone_get_code)
    void getPhoneCode() {
        String mobileNumberString = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(mobileNumberString)) {
            ToastUtil.ToastMessage(this, "请输入手机号", ToastUtil.WARN);
            return;
        }
        mTxtGetCode.setClickable(false);
        Map<String,Object> params = new HashMap<>();
        params.put("phone", ToolUtil.changeString(mEtPhone.getText()));
        WebUtil.getInstance().requestPOST(this, URLConstant.GET_PHONE_CODE, params, true, true, new WebUtil.WebCallBack<Object>() {
            @Override
            public void onWebSuccess(JSONObject result) {
                Log.d("Register", result.toString());
                ToastUtil.ToastMessage(AlterRegPhoneActivity.this, "发送验证码成功");
                timeTask = new TimeTask();
                timeTask.execute();
            }

            @Override
            public void onWebFailed(String errorMsg) {
                Log.d("Register", errorMsg);

                ToastUtil.ToastMessage(AlterRegPhoneActivity.this, "发送验证码失败", ToastUtil.WRONG);
                mTxtGetCode.setClickable(true);
            }
        });
    }

    /**
     * 获取 验证码倒计时
     */
    class TimeTask extends AsyncTask<Void, Integer, Boolean> {
        int time = 60;
        @Override
        protected void onPreExecute() {
            mTxtGetCode.setText("(60s)");
            //    mTxtGetCode.setBackgroundResource(R.color.silver);
            mTxtGetCode.setClickable(false);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mTxtGetCode.setText("获取验证码");
            time = 60;
            mTxtGetCode.setBackgroundResource(R.color.colorPrimary);
            mTxtGetCode.setClickable(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mTxtGetCode.setText("(" + values[0] + "s)");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            while (true) {
                if (time >=0) {
                    try {
                        Thread.sleep(1000);
                        time--;
                        publishProgress(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return false;
                    }
                } else {
                    return true;
                }
            }
        }

        @Override
        protected void onCancelled() {
            mTxtGetCode.setText("获取验证码");
            time = 60;
            //    mTxtGetCode.setBackgroundResource(R.color.colorPrimary);
            mTxtGetCode.setClickable(true);
        }
    }
}
