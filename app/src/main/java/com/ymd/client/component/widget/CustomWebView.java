package com.ymd.client.component.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

/***
 * 类名:CustomWebView
 * 功能简介：自定义WebView，长按图片获取图片url
 * 修改历史：
 */
public class CustomWebView extends WebView implements OnLongClickListener {
    private Context context;
    private LongClickCallBack mCallBack;


    public CustomWebView(Context context) {
        super(context);
        this.context = context;
        initSettings();
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initSettings();
    }


    public void setmCallBack(LongClickCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }


    private void initSettings() {
        // 初始化设置
        WebSettings mSettings = this.getSettings();
        mSettings.setJavaScriptEnabled(true);//开启javascript
        mSettings.setDomStorageEnabled(true);//开启DOM
        mSettings.setDefaultTextEncodingName("utf-8");//设置字符编码
        //设置web页面
        mSettings.setAllowFileAccess(true);//设置支持文件流
        mSettings.setSupportZoom(true);// 支持缩放
        mSettings.setDisplayZoomControls(false); // 设置隐藏缩放控制控件（+ - 号）
        mSettings.setUseWideViewPort(true);// 调整到适合webview大小
        mSettings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mSettings.setBuiltInZoomControls(true);// 设置出现缩放工具
        //mSettings.setDefaultZoom(ZoomDensity.FAR);// 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自适应屏幕
        setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        // 长按事件监听（注意：需要实现LongClickCallBack接口并传入对象）
        final HitTestResult htr = getHitTestResult();//获取所点击的内容
        if (htr.getType() == HitTestResult.IMAGE_TYPE) {//判断被点击的类型为图片
            mCallBack.onLongClickCallBack(htr.getExtra());
        }
        return false;
    }


    /**
     * 长按事件回调接口，传递图片地址
     *
     * @author LinZhang
     */
    public interface LongClickCallBack {
        /**
         * 用于传递图片地址
         */
        void onLongClickCallBack(String imgUrl);
    }

}
