package com.ymd.client.component.activity.login;

import android.app.Activity;
import android.content.Context;
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
import com.ymd.client.component.event.LoginEvent;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.FastDoubleClickUtil;
import com.ymd.client.utils.LogUtil;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.web.WebUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

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
    private TextView registerBtn;
    private TextView forgetPasswordBtn;

    private String index;

    public static boolean isFront  = false;


    @Override
    public void back(View view) {
        if (index.contains("Login")) {
            LoginActivity.startAction(this);
        }
        finish();
    }

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Context context) {
        if (!FastDoubleClickUtil.isFastDoubleClick()) {
            Intent intent = new Intent(context, LoginByPWActivity.class);
            LogUtil.showD(context.getClass().getName());
            intent.putExtra("index", context.getClass().getName());
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_pw);
        initView();
    }


    private void initView() {
        setStatusBar(R.color.bg_header);
        registerBtn = (TextView) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.startAction(LoginByPWActivity.this);
                finish();
            }
        });

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
        setRightBtn("验证码登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.startAction(LoginByPWActivity.this);
                finish();
            }
        });
        index = getIntent().getExtras().getString("index");
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

        Map<String, Object> params = new HashMap<>();
        params.put("password", mobileCodeString);
        params.put("phone", mobileNumberString);
        params.put("role", "0");
        params.put("type", "0");
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

    @Override
    protected void onStart() {
        super.onStart();
        isFront = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFront = false;
    }
}
