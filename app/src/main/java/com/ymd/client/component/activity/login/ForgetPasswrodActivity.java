package com.ymd.client.component.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.main.MainActivity;
import com.ymd.client.component.activity.mine.setting.config.AlterRegPhoneActivity;
import com.ymd.client.model.constant.URLConstant;
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
 * 包名:com.ymd.client.component.activity.login
 * 类名:
 * 时间:2018/8/29 0021Time:16:08
 * 作者:荣维鹤
 * 功能简介: 忘记密码
 * 修改历史:
 */
public class ForgetPasswrodActivity extends BaseActivity {

    @BindView(R.id.mobileNumber)
    EditText mobileNumber;
    @BindView(R.id.mobileCode)
    EditText mobileCode;
    @BindView(R.id.mobileCodeBtn)
    TextView mobileCodeBtn;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.login_btn)
    Button loginBtn;

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, ForgetPasswrodActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passwrod);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        setTitle("忘记密码");
        setStatusBar(R.color.bg_header);
        mobileCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoneCode();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
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
        String password = passwordEt.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastUtil.ToastMessage(this, "请输入密码", ToastUtil.WARN);
            return;
        }
        if (!ToolUtil.isLetterDigit(password)) {
            ToastUtil.ToastMessage(this, "新密码格式不正确", ToastUtil.WARN);
            return;
        }

        Map<String,Object> params = new HashMap<>();
        params.put("phone", mobileNumberString);
        params.put("code", mobileCodeString);
        params.put("password", password);
        params.put("type",0);
        WebUtil.getInstance().requestPOST(this, URLConstant.CHANGE_LOGIN_PASSWORD, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        ToastUtil.ToastMessage(getApplicationContext(), "密码修改成功");
                        finish();
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                        ToastUtil.ToastMessage(getApplicationContext(), "密码修改失败");
                    }
                });

    }

    void getPhoneCode() {
        String mobileNumberString = mobileNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mobileNumberString)) {
            ToastUtil.ToastMessage(this, "请输入手机号", ToastUtil.WARN);
            return;
        }
        mobileCodeBtn.setClickable(false);
        Map<String,Object> params = new HashMap<>();
        params.put("phone", ToolUtil.changeString(mobileNumber.getText()));
        WebUtil.getInstance().requestPOST(this, URLConstant.GET_PHONE_CODE, params, false, true, new WebUtil.WebCallBack<Object>() {
            @Override
            public void onWebSuccess(JSONObject result) {
                Log.d("Register", result.toString());
                ToastUtil.ToastMessage(ForgetPasswrodActivity.this, "发送验证码成功");
                timeTask = new TimeTask();
                timeTask.execute();
            }

            @Override
            public void onWebFailed(String errorMsg) {
                Log.d("Register", errorMsg);

                ToastUtil.ToastMessage(ForgetPasswrodActivity.this, "发送验证码失败", ToastUtil.WRONG);
                mobileCodeBtn.setClickable(true);
            }
        });
    }
    private TimeTask timeTask;

    /**
     * 获取 验证码倒计时
     */
    class TimeTask extends AsyncTask<Void, Integer, Boolean> {
        int time = 60;
        @Override
        protected void onPreExecute() {
            mobileCodeBtn.setText("(60s)");
            //    mobileCodeBtn.setBackgroundResource(R.color.silver);
            mobileCodeBtn.setClickable(false);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mobileCodeBtn.setText("获取验证码");
            time = 60;
            mobileCodeBtn.setBackgroundResource(R.color.colorPrimary);
            mobileCodeBtn.setClickable(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mobileCodeBtn.setText("(" + values[0] + "s)");
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
            mobileCodeBtn.setText("获取验证码");
            time = 60;
            //    mobileCodeBtn.setBackgroundResource(R.color.colorPrimary);
            mobileCodeBtn.setClickable(true);
        }
    }
}
