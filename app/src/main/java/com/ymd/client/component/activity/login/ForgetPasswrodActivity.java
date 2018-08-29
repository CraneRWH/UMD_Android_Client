package com.ymd.client.component.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.main.MainActivity;
import com.ymd.client.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        MainActivity.startAction(this);
        finish();
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

    }

    private TimeTask timeTask;
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
