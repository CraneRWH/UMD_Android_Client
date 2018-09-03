package com.ymd.client.component.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.main.MainActivity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.web.WebUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 包名:com.ymd.client.component.activity.login
 * 类名:
 * 时间:2018/8/21 0021Time:16:08
 * 作者:荣维鹤
 * 功能简介:
 * 修改历史:
 */
public class LoginByPWActivity extends BaseActivity {

    private EditText mobileNumber;
    private EditText passwordEt;
    private Button loginBtn;
    private TextView forgetPasswordBtn;

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, LoginByPWActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_pw);
        initView();
    }

    private void initView() {
        setStatusBar(R.color.bg_header);

        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        mobileNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        passwordEt = (EditText) findViewById(R.id.password_et);
        passwordEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        forgetPasswordBtn = (TextView) findViewById(R.id.forget_password_btn);
        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswrodActivity.startAction(LoginByPWActivity.this);
            }
        });
    }

    private void submit() {


        String mobileNumberString = mobileNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mobileNumberString)) {
            ToastUtil.ToastMessage(this, "请输入手机号", ToastUtil.WARN);
            return;
        }

        String mobileCodeString = passwordEt.getText().toString().trim();
        if (TextUtils.isEmpty(mobileCodeString)) {
            ToastUtil.ToastMessage(this, "请输入密码", ToastUtil.WARN);
            return;
        }

        Map<String,Object> params = new HashMap<>();
        params.put("password", mobileCodeString);
        params.put("phone", mobileNumberString);
        params.put("role", "0");
        WebUtil.getInstance().requestPOST(this, URLConstant.LOGIN, params, new WebUtil.WebCallBack() {
            @Override
            public void onWebSuccess(String result) {
                toMaiin();
            }

            @Override
            public void onWebFailed(String errorMsg) {

            }
        });
    }

    private void toMaiin() {
        MainActivity.startAction(this);
        finish();
    }

}
