package com.ymd.client.common.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.ymd.client.R;
import com.ymd.client.UApplication;
import com.ymd.client.common.base.swipeback.ui.SwipeBackActivity;
import com.ymd.client.component.event.BaseEvent;
import com.ymd.client.component.widget.progress.ProgressHUD;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.ACache;
import com.ymd.client.utils.ActivityTack;
import com.ymd.client.utils.ScreenUtil;
import com.ymd.client.utils.SingleToast;
import com.ymd.client.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.lang.reflect.Constructor;

/**
 * @author zhl
 * @class com.ymaidan.client.ui
 * @time 2018/6/12 0012 16:18
 * @description UI层实现类
 */
public abstract class BaseActivity<T extends IBasePresenter, K> extends SwipeBackActivity implements IBaseView {

    protected T mPresenter;

    /**
     * 全局的LayoutInflater对象，已经完成初始化.
     */
    protected LayoutInflater mInflater = null;
    protected RelativeLayout container = null;
    protected RelativeLayout mTitlebar = null;
    protected RelativeLayout.LayoutParams lp = null;

    protected View mParentView = null;
    // 共用的声明部分
    protected TextView titleText = null;
    protected ImageView logoView = null;
    protected LinearLayout rightLayout = null;
    RelativeLayout.LayoutParams mLayoutParamsRight = null;

    protected ProgressHUD progressBar = null;
    ACache aCache;
    ActivityTack activityTack = ActivityTack.getInstanse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //支持横竖屏切换
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        setContentView(R.layout.layout_title_base);

        initPresenter();
        initBase();

        // 设置沉浸式状态栏
        initStatusBar();
        //初始化标题栏
        initTitleBar();
        initViews();

        initDir();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter.clear();
        }
    }

    @Subscribe
    public void onEventMainThread(BaseEvent event) {
        //...doSomething...
    }

    /**
     * 初始化布局
     */
    protected abstract void initViews();

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        finish();
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

    /**
     * 设置沉浸式状态栏
     */
    protected void initStatusBar() {
        StatusBarUtil.setColorForSwipeBack(this, getResources().getColor(R.color.common_color_bg), 0);
        //StatusBarUtil.StatusBarLightMode(this, true);
    }

    /**
     * 初始化标题栏
     */
    private void initTitleBar() {
        titleText = (TextView) findViewById(R.id.base_title);
        logoView = (ImageView) findViewById(R.id.base_back);
        rightLayout = (LinearLayout) findViewById(R.id.base_right);
        container = (RelativeLayout) findViewById(R.id.container);
        mTitlebar = (RelativeLayout) findViewById(R.id.base_titlebar);

        mLayoutParamsRight = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mLayoutParamsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mLayoutParamsRight.addRule(RelativeLayout.CENTER_VERTICAL);
        rightLayout.setLayoutParams(mLayoutParamsRight);

        logoView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化
     */
    private void initBase() {
        mInflater = LayoutInflater.from(this);
        aCache = ACache.get(this);
        EventBus.getDefault().register(this);
        activityTack.addActivity(this);
    }

    /**
     * 设置要显示的布局方法
     */
    public void setAbContentView(int layoutID) {
        mParentView = mInflater.inflate(layoutID, null);
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.BELOW, R.id.base_titlebar);

        container.addView(mParentView, lp);
    }

    /**
     * 获得 title
     */
    public TextView getTitleText() {
        return titleText;
    }

    /**
     * 设置标题
     */
    public void setTitleText(String text) {
        titleText.setText(text);
    }

    /**
     * 设置标题
     */
    public void setTitleText(int text) {
        titleText.setText(getString(text));
    }

    /**
     * 获得左侧 按钮
     */
    public ImageView getLogoView() {
        return logoView;
    }

    /**
     * 描述：把指定的View填加到标题栏右边.
     *
     * @param rightView 指定的View
     */
    public void addRightView(View rightView) {
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.addView(rightView, mLayoutParamsRight);
    }

    /**
     * 描述：把指定资源ID表示的View填加到标题栏右边.
     *
     * @param resId 指定的View的资源ID
     */
    public void addRightView(int resId) {
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.addView(mInflater.inflate(resId, null), mLayoutParamsRight);
    }

    /**
     * 描述：清除标题栏右边的View.
     */
    public void clearRightView() {
        rightLayout.removeAllViews();
    }

    /**
     * 显示左侧logo
     */
    public void setLogoViewVisible() {
        logoView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏左侧logo
     */
    public void setLogoViewGone() {
        logoView.setVisibility(View.GONE);
    }

    /**
     * 设置标题栏是否可见
     *
     * @param flag
     */
    public void setTitleBarVisiable(boolean flag) {
        if (flag) {
            mTitlebar.setVisibility(View.VISIBLE);
        } else
            mTitlebar.setVisibility(View.GONE);
    }

    /**
     * 返回逻辑处理的具体类型.
     */
    protected abstract Class<T> getPresenterClass();

    /**
     * 返回View层的接口类.
     */
    protected abstract Class<K> getViewClass();

    protected void initPresenter() {
        try {
            Constructor constructor = getPresenterClass().getConstructor(getViewClass());
            mPresenter = (T) constructor.newInstance(this);
        } catch (Exception e) {
            KLog.d("init presenter has exception");
        }
    }

    @Override
    public void showError(String msg) {
        toast(msg);
    }

    @Override
    public void toast(String str) {
        new SingleToast(this).showButtomToast(str);
    }

    @Override
    public void toast(int id) {
        new SingleToast(this).showButtomToast(id);
    }

    private void initDir() {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + Constants.ROOT_PATH + File.separator
                + UApplication.getGobalApplication().getPackageName());

        File dirFile = new File(file.getAbsolutePath());
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        } else {
        }
    }

    /**
     * 跳转页面
     *
     * @param activity 原页面
     * @param clazz    目标页面
     */
    public void startActivityWithNoData(Activity activity, Class<? extends Activity> clazz) {
        Intent intent = new Intent(activity, clazz);
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param activity 原页面
     * @param clazz    目标页面
     * @param bundle   参数
     */
    public void startActivityWithData(Activity activity, Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 打开 APP 的详情设置
     */
    protected void openAppDetails(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message + "，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /**
     * dip转为px
     *
     * @return
     */
    protected int dip2px(float px) {
        return ScreenUtil.dip2px(this, px);
    }


    @Override
    public void showProgress() {
        //先用这个，换了再改
        if (progressBar == null) {
            progressBar = ProgressHUD.create(this)
                    .setLabel(getResources().getString(R.string.default_progress_text))
                    //.setDimAmount(0.5f)
                    .setStyle(ProgressHUD.Style.SPIN_INDETERMINATE);
        }
        progressBar.show();
    }

    @Override
    public void hideProgress() {
        if (progressBar != null && progressBar.isShowing()) {
            progressBar.dismiss();
        }
    }

    @Override
    public void toLogin() {
    }
}
