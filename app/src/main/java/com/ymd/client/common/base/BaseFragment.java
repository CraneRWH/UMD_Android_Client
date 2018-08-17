package com.ymd.client.common.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.socks.library.KLog;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.ymd.client.R;
import com.ymd.client.UApplication;
import com.ymd.client.component.widget.progress.ProgressHUD;
import com.ymd.client.utils.ACache;
import com.ymd.client.utils.SingleToast;

import java.lang.reflect.Constructor;

/**
 * @author zhl
 * @class com.ymaidan.client.base
 * @time 2018/6/14 0014 16:53
 * @description
 */
public abstract class BaseFragment<T extends IBasePresenter, K> extends RxFragment implements IBaseView {

    protected T mPresenter;
    private ProgressHUD progressBar = null;
    ACache aCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        aCache = ACache.get(UApplication.getGobalApplication());

    }

    private void initPresenter() {
        try {
            Constructor constructor = getPresenterClass().getConstructor(getViewClass());
            mPresenter = (T) constructor.newInstance(this);
        } catch (Exception e) {
             KLog.d("init presenter has exception");
        }
    }

    /**
     * 返回逻辑处理的具体类型.
     */
    protected abstract Class<T> getPresenterClass();

    /**
     * 返回View层的接口类.
     */
    protected abstract Class<K> getViewClass();

    /**
     * 跳转页面
     *
     * @param activity 原页面
     * @param clazz    目标页面
     */
    public void startActivityWithNoData(Context activity, Class<? extends Activity> clazz) {
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
    public void startActivityWithData(Context activity, Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.clear();
        }
    }

    @Override
    public void showError(String msg) {
        toast(msg);
    }

    @Override
    public void toast(String str) {
        new SingleToast(getContext()).showButtomToast(str);
    }

    @Override
    public void toast(int id) {
        new SingleToast(getContext()).showButtomToast(id);
    }

    @Override
    public void showProgress() {
        //先用这个，换了再改
        if (progressBar == null) {
            progressBar = ProgressHUD.create(getContext())
                    .setLabel(getResources().getString(R.string.default_progress_text))
                    .setDimAmount(0.5f)
                    .setStyle(ProgressHUD.Style.SPIN_INDETERMINATE);
        }
        progressBar.show();
    }

    @Override
    public void hideProgress() {
        if (progressBar != null) {
            progressBar.dismiss();
        }
    }

    @Override
    public void toLogin() {
    }
}
