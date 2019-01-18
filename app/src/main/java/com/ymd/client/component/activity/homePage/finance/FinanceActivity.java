package com.ymd.client.component.activity.homePage.finance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.widget.CustomWebView;
import com.ymd.client.component.widget.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2019/1/18
 * 描述:    酒店、爱车、美容美发、电影、生鲜、金融、洗浴/KTV、优币专区、其他分类等
 * 修改历史:
 */
public class FinanceActivity extends BaseActivity {

    @BindView(R.id.webView)
    CustomWebView mWebView;

    private LoadingDialog pdg;
    private boolean loadError;//标记网页加载失败

    private String url = "https://tjslc.tjfae.com/tjfae/html/usermgmt/usermgmt-login.html?channelOid=8a7df195684bfabc01684f8867d803ec";
    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, FinanceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        pdg = new LoadingDialog(this);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            /**
             * 网页页面开始加载的时候，执行的回调方法
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {//网页页面开始加载的时候
                super.onPageStarted(view, url, favicon);
            }

            /**
             * 网页加载结束的时候执行的回调方法
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {//网页加载结束的时候
                setLoadView();
            }


            /**
             * 页面加载错误时执行的方法，但是在6.0以下，有时候会不执行这个方法
             * @param view
             * @param errorCode
             * @param description
             * @param failingUrl
             */
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                loadError = true;
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
                view.requestFocus();
                view.requestFocusFromTouch();
            }
        });

        // 自定义客户端
        mWebView.setWebChromeClient(new WebChromeClient() {

            /**
             * 当WebView加载之后，返回 HTML 页面的标题 Title
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
                if (!TextUtils.isEmpty(title) && title.toLowerCase().contains("error")) {
                    loadError = true;
                }
                setLoadView();
            }
        });

        loadData(url);
    }

    /***
     * 根据网址加载数据显示在页面上
     */
    private void loadData(String mUrl) {
        loadError = false;//重新加载时，将加载失败的标记重置
        pdg.show();//显示加载框
        mWebView.clearCache(true); // 清除缓存数据
        mWebView.loadUrl(mUrl);
    }

    /**
     * 网页加载后显示视图
     */
    private void setLoadView() {
        pdg.dismiss();
        //网页加载失败，显示空视图
        if (loadError) {
            mWebView.setVisibility(View.GONE);
        } else { //加载成功，显示网页
            mWebView.setVisibility(View.VISIBLE);
        }
    }
}
