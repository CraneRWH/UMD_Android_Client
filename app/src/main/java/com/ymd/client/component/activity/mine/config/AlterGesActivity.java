package com.ymd.client.component.activity.mine.config;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.widget.lock.LockPatternUtil;
import com.ymd.client.component.widget.lock.LockPatternView;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.ACache;
import com.ymd.client.utils.StatusBarUtils;
import com.ymd.client.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改手势密码
 */
public class AlterGesActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @BindView(R.id.verify_gesture_lock)
    LockPatternView lockPatternView;
    @BindView(R.id.verify_gesture_message)
    TextView messageTv;
    @BindView(R.id.verify_gesture_forget)
    TextView forgetGestureBtn;
    @BindView(R.id.verify_gesture_other)
    TextView otherLoginBtn;

    private ACache aCache;
    private static final long DELAYTIME = 600L;
    private byte[] gesturePassword;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_ges);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(R.string.setting_alter_ges_pw);

        init();
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

    private void init() {
        aCache = ACache.get(this);

        //得到当前用户的手势密码
        gesturePassword = aCache.getAsBinary(Constants.GES_KEY);
        lockPatternView.setOnPatternListener(patternListener);
        updateStatus(Status.DEFAULT);
    }

    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if (pattern != null) {
                if (LockPatternUtil.checkPattern(pattern, gesturePassword)) {
                    updateStatus(Status.CORRECT);
                } else {
                    updateStatus(Status.ERROR);
                }
            }
        }
    };

    @OnClick({R.id.verify_gesture_forget, R.id.verify_gesture_other})
    void click(View view) {
        switch (view.getId()) {
            case R.id.verify_gesture_other:
                login();
                break;
            case R.id.verify_gesture_forget:
                submit();
                break;
        }

    }

    void submit() {
        ToastUtil.ToastMessage(this, "忘记手势密码");
        aCache.remove(Constants.GES_KEY);
    }

    void login() {
        ToastUtil.ToastMessage(this, "其他方式验证手势");
    }


    /**
     * 更新状态
     *
     * @param status
     */
    private void updateStatus(Status status) {
        messageTv.setText(status.strId);
        messageTv.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case ERROR:
                if (count > 4) {
                    lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                    lockPatternView.setFocusable(false);
                    showForgetDialog();
                    return;
                }
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                count++;
                break;
            case CORRECT:
                if (count > 4) {
                    lockPatternView.setFocusable(false);
                    lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                    showForgetDialog();
                    return;
                }
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                verifySuccess();
                count++;
                break;
        }
    }

    private void showForgetDialog() {
        //弹出对话框，提示忘记手势密码
        ToastUtil.ToastMessage(this, "忘记手势密码");
        aCache.remove(Constants.GES_KEY);
    }

    public void verifySuccess() {
        ToastUtil.ToastMessage(this, "验证成功");
        aCache.remove(Constants.GES_KEY);
        startActivity(new Intent(this, SetGesActivity.class));
        finish();
    }

    private enum Status {
        //默认的状态
        DEFAULT(R.string.gesture_default, R.color.grey_a5a5a5),
        //密码输入错误
        ERROR(R.string.gesture_error, R.color.red),
        //密码输入正确
        CORRECT(R.string.gesture_correct, R.color.grey_a5a5a5);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }
}
