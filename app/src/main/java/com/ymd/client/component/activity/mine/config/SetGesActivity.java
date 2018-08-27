package com.ymd.client.component.activity.mine.config;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.widget.lock.GestureStatus;
import com.ymd.client.component.widget.lock.LockPatternIndicator;
import com.ymd.client.component.widget.lock.LockPatternUtil;
import com.ymd.client.component.widget.lock.LockPatternView;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.ACache;
import com.ymd.client.utils.StatusBarUtils;
import com.ymd.client.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置手势密码，保存手势密码到本地
 */
public class SetGesActivity extends BaseActivity implements LockPatternView.OnPatternListener {

    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @BindView(R.id.set_gesture_lock_indicator)
    LockPatternIndicator lockPatternIndicator;
    @BindView(R.id.set_gesture_lock)
    LockPatternView lockPatternView;
    @BindView(R.id.set_gesture_message)
    TextView messageTv;

    private List<LockPatternView.Cell> mChosenPattern = null;
    private ACache aCache;
    private static final long DELAYTIME = 600L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ges);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(R.string.setting_ges_pw);

        init();
    }

    @OnClick(R.id.base_back)
    void back() {
        finish();
    }

    private void init() {
        aCache = ACache.get(this);
        lockPatternView.setOnPatternListener(this);
    }

    @Override
    protected void setStatusBar(int resourcesId) {
        super.setStatusBar(resourcesId);
        StatusBarUtils.StatusBarLightMode(this, true);
    }

    @Override
    public void onPatternStart() {
        lockPatternView.removePostClearPatternRunnable();
        lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
    }

    @Override
    public void onPatternComplete(List<LockPatternView.Cell> cells) {
        if (mChosenPattern == null && cells.size() >= 4) {
            mChosenPattern = new ArrayList<LockPatternView.Cell>(cells);
            updateStatus(GestureStatus.CREATE_CORRECT, cells);
        } else if (mChosenPattern == null && cells.size() < 4) {
            updateStatus(GestureStatus.CREATE_LESSERROR, cells);
        } else if (mChosenPattern != null) {
            if (mChosenPattern.equals(cells)) {
                updateStatus(GestureStatus.CREATE_CONFIRMCORRECT, cells);
            } else {
                updateStatus(GestureStatus.CREATE_CONFIRMERROR, cells);
            }
        }
    }

    /**
     * 更新状态
     *
     * @param status
     * @param pattern
     */
    private void updateStatus(GestureStatus status, List<LockPatternView.Cell> pattern) {
        messageTv.setTextColor(getResources().getColor(status.colorId));
        messageTv.setText(status.strId);
        switch (status) {
            case CREATE_DEFAULT:
                //刚进去的时候  // 重试的时候
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case CREATE_CORRECT:
                updateLockPatternIndicator();
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case CREATE_LESSERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case CREATE_CONFIRMERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case CREATE_CONFIRMCORRECT:
                saveChosenPattern(pattern);
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                setLockPatternSuccess();
                break;
        }
    }

    /**
     * 更新 Indicator
     */
    private void updateLockPatternIndicator() {
        if (mChosenPattern == null)
            return;
        lockPatternIndicator.setIndicator(mChosenPattern);
    }

    /**
     * 成功设置了手势密码(跳到首页)
     */
    private void setLockPatternSuccess() {
        ToastUtil.ToastMessage(this, "创建手势成功");
    }

    /**
     * 保存手势密码
     */
    private void saveChosenPattern(List<LockPatternView.Cell> cells) {
        byte[] bytes = LockPatternUtil.patternToHash(cells);
        aCache.put(Constants.GES_KEY, bytes);
        finish();
    }

    @OnClick({R.id.set_gesture_reset})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.set_gesture_reset:
                mChosenPattern = null;
                lockPatternIndicator.setDefaultIndicator();
                updateStatus(GestureStatus.CREATE_DEFAULT, null);
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            default:
                break;
        }
    }
}
