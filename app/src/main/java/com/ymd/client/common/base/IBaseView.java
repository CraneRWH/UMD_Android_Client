package com.ymd.client.common.base;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @author zhl
 * @time 2018/6/12 0012 16:12
 * @description V层基类
 */
public interface IBaseView {

    void showProgress();//可以显示进度条

    void hideProgress();//可以隐藏进度条

    void showError(String msg);//发生错误就显示错误信息

    void toast(String str);

    void toast(int id);

    void toLogin();//去登录

    <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event);
}
