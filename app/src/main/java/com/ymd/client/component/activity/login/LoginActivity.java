package com.ymd.client.component.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.main.MainActivity;
import com.ymd.client.utils.ToastUtil;

/**
 * 作者:rongweihe
 * 日期:2018/8/22  时间:22:56
 * 描述:  使用手机验证码登录
 * 修改历史:
 */
public class LoginActivity extends BaseActivity {

    private EditText mobileNumber;
    private EditText mobileCode;
    private Button mobileCodeBtn;
    private Button loginBtn;
    private ImageView weixinIv;
    private ImageView qqIv;
    private ImageView weiboIv;

    private TimeTask timeTask;

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        //    setStatusBar(R.color.head_white);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        mobileCode = (EditText) findViewById(R.id.mobileCode);
        mobileCodeBtn = (Button) findViewById(R.id.mobileCodeBtn);
        loginBtn = (Button) findViewById(R.id.login_btn);
        weixinIv = (ImageView) findViewById(R.id.weixin_iv);
        qqIv = (ImageView) findViewById(R.id.qq_iv);
        weiboIv = (ImageView) findViewById(R.id.weibo_iv);

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

        setTitle("登录");
        setRightBtn("密码登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginByPWActivity.startAction(LoginActivity.this);
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

        MainActivity.startAction(this);
        finish();
    }

    private void getPhoneCode() {
        mobileCodeBtn.setClickable(false);
        timeTask = new TimeTask();
        timeTask.execute();
    }

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
