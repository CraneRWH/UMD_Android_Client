package com.ymd.client.component.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.event.LoginEvent;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/22  时间:22:56
 * 描述:  使用手机验证码登录
 * 修改历史:
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.mobile_code_rlt)
    RelativeLayout mobileCodeRlt;
    private EditText mobileNumber;
    private EditText mobileCode;
    private TextView mobileCodeBtn;
    private Button loginBtn;
    private ImageView weixinIv;
    private ImageView qqIv;
    private ImageView weiboIv;

    private TimeTask timeTask;

    private TextView registerBtn;
    private String index;

    private boolean isMobileCode = true;
    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("index", context.getClass().getName());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setStatusBar(R.color.white);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        mobileCode = (EditText) findViewById(R.id.mobileCode);
        mobileCodeBtn = (TextView) findViewById(R.id.mobileCodeBtn);
        loginBtn = (Button) findViewById(R.id.login_btn);
        weixinIv = (ImageView) findViewById(R.id.weixin_iv);
        qqIv = (ImageView) findViewById(R.id.qq_iv);
        weiboIv = (ImageView) findViewById(R.id.weibo_iv);
        registerBtn = (TextView) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.startAction(LoginActivity.this);
                finish();
            }
        });
        mobileCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoneCode();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMobileCode) {
                    getPhoneCode();
                } else {
                    submit();
                }
            }
        });

        setTitle("");
        setRightImg(R.mipmap.icon_login_by_password, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginByPWActivity.startAction(LoginActivity.this);
                finish();
            }
        });
        index = getIntent().getExtras().getString("index");
    }

    private void submit() {
        // validate
        String mobileNumberString = mobileNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mobileNumberString)) {
            ToastUtil.ToastMessage(this, "请输入手机号", ToastUtil.WARN);
            return;
        }

        String mobileCodeString = mobileCode.getText().toString().trim();
        if (TextUtils.isEmpty(mobileCodeString)) {
            ToastUtil.ToastMessage(this, "请输入验证码", ToastUtil.WARN);
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("code", mobileCodeString);
        params.put("phone", mobileNumberString);
        params.put("role", "0");
        params.put("type", "1");
        WebUtil.getInstance().requestPOST(this, URLConstant.LOGIN, params, new WebUtil.WebCallBack() {
            @Override
            public void onWebSuccess(JSONObject result) {
                toMaiin(result);
            }

            @Override
            public void onWebFailed(String errorMsg) {

            }
        });

    }

    private void toMaiin(JSONObject jsonObject) {
        JSONObject userStr = jsonObject.optJSONObject("user");
        LoginInfo.setLoginInfo(userStr.toString());
        CommonShared.setString(CommonShared.LOGIN_TOKEN, jsonObject.optString("token"));
        //    MainActivity.startAction(this);
        EventBus.getDefault().post(new LoginEvent(true));
        finish();
    }

    private void getPhoneCode() {
        String mobileNumberString = mobileNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mobileNumberString)) {
            ToastUtil.ToastMessage(this, "请输入手机号", ToastUtil.WARN);
            return;
        }
        mobileCodeBtn.setClickable(false);
        Map<String, Object> params = new HashMap<>();
        params.put("phone", ToolUtil.changeString(mobileNumber.getText()));
        WebUtil.getInstance().requestPOST(this, URLConstant.GET_PHONE_CODE, params, false, true, new WebUtil.WebCallBack<Object>() {
            @Override
            public void onWebSuccess(JSONObject result) {
                Log.d("Register", result.toString());
                ToastUtil.ToastMessage(LoginActivity.this, "发送验证码成功");
                mobileCodeRlt.setVisibility(View.VISIBLE);
                loginBtn.setBackgroundResource(R.mipmap.icon_login_btn);
                isMobileCode = false;
                timeTask = new TimeTask();
                timeTask.execute();
            }

            @Override
            public void onWebFailed(String errorMsg) {
                Log.d("Register", errorMsg);

                mobileCodeRlt.setVisibility(View.GONE);
                ToastUtil.ToastMessage(LoginActivity.this, "发送验证码失败", ToastUtil.WRONG);
                mobileCodeBtn.setClickable(true);
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
            mobileCodeBtn.setText("60S");
            //    mobileCodeBtn.setBackgroundResource(R.color.silver);
            mobileCodeBtn.setClickable(false);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mobileCodeBtn.setText("获取验证码");
            time = 60;
            mobileCodeBtn.setClickable(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mobileCodeBtn.setText(values[0] + "S");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            while (true) {
                if (time >= 0) {
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
            mobileCodeBtn.setText("获取验证码");
            time = 60;
        //    mobileCodeBtn.setBackgroundResource(R.color.bg_header);
            mobileCodeBtn.setClickable(true);
        }
    }
}
