package com.ymd.client.component.activity.mine.setting.config;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
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
 * 修改登陆密码
 */
public class AlterLoginPwActivity extends BaseActivity {

    @BindView(R.id.alter_pw_old)
    EditText mEtOldPw;
    @BindView(R.id.alter_pw_new)
    EditText mEtNewPw;
    @BindView(R.id.alter_pw_confirm)
    EditText mEtConfimPw;
    @BindView(R.id.alter_pw_submit)
    TextView alterPwSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_login_pw);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        setTitle("修改登录密码");
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

    @OnClick(R.id.alter_pw_submit)
    void submit() {
        String oldPassword = ToolUtil.changeString(mEtOldPw.getText());
        if (oldPassword.length() == 0) {
            ToastUtil.ToastMessage(this, "请输入旧密码");
            return;
        }
        String newPassword = ToolUtil.changeString(mEtNewPw.getText());
        if (newPassword.length() == 0) {
            ToastUtil.ToastMessage(this, "请输入新密码");
            return;
        }
        String confimPassword = ToolUtil.changeString(mEtConfimPw.getText());
        if (confimPassword.length() == 0) {
            ToastUtil.ToastMessage(this, "请再次输入新密码");
            return;
        }
        if (!newPassword.equals(confimPassword)) {
            ToastUtil.ToastMessage(this, "两次新密码输入不一致");
            return;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("oldPw", oldPassword);
        params.put("password", newPassword);
        params.put("password2", confimPassword);
        params.put("phone", LoginInfo.getInstance().getLoginInfo().getPhone());
        params.put("type",1);
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

}
