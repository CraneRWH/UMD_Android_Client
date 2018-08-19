package com.ymd.client.utils;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;


/**
 * 包名:cn.school.schoolbus.utils
 * 类名:StatusBarUtils
 * 功能简介：状态栏工具类,主要实现沉浸式状态栏显示
 * 修改历史：
 */

public class StatusBarUtils {

    private View statusView;// 状态栏View

    private static StatusBarUtils instance = new StatusBarUtils();

    private StatusBarUtils() {
    }

    public static StatusBarUtils getInstance() {
        return instance;
    }

    /**
     * 当某些情况下，需要隐藏掉已经设置颜色的状态栏
     *
     * @param activity
     */
    public void hideStatusBar(Activity activity) {
        // 只有在19及以上可以实现沉浸式状态栏样式修改
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            if (decorView != null && statusView != null) {
                decorView.removeView(statusView);
            }
        }
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusColor(Activity activity, int resources) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            statusView = createStatusBarView(activity, resources);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            if (rootView != null) {
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
            }
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     */
    private View createStatusBarView(Activity activity, int resourceId) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundResource(resourceId);
        return statusBarView;
    }

    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
