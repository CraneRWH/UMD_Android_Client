package com.ymd.client.common.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.utils.StatusBarUtils;


/**
 * 类名:BaseActivity
 * 功能简介：抽出基础的通用Activity
 * 修改历史：
 */

public class BaseActivity extends AppCompatActivity {

    // 维持一个context，方便子类Toast或者其它要使用context的地方获取
    public Context currentactivity;
    private int headResourcesID = R.color.bg_header;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        currentactivity = this;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar(headResourcesID);
    }

    /**
     * 接收上一个界面传递的数据
     */
    protected void receiveData() {}

    /**
     * 设置状态栏颜色
     *
     * @param resourcesId 资源ID，可以是颜色，也可是图片
     */
    protected void setStatusBar(int resourcesId) {
        StatusBarUtils.getInstance().setStatusColor(this, resourcesId);
    }

    /**
     * 隐藏已经显示的状态栏
     */
    protected void setStatusBarHide() {
        StatusBarUtils.getInstance().hideStatusBar(this);
    }

    public String getPageName() {
        return this.getClass().getName();
    }

    @Override
    protected void onResume() {
        super.onResume();
    //    handlerNitifyer(getIntent());
        // 控制字体不随系统设置变化，避免字体变化导致的布局错乱的问题
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1.0f;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
     //   AliHttpUtil.logRequest();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 左上角返回按钮
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 设置标题
     *
     * @param title 标题字符串
     */
    public void setTitle(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.txtTitle);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    /**
     * 设置标题
     *
     * @param titleId 标题内容对应的字符串ID
     */
    @Override
    public void setTitle(int titleId) {
        TextView tvTitle = (TextView) findViewById(R.id.txtTitle);
        if (tvTitle != null) {
            tvTitle.setText(titleId);
        }
    }


    /**
     * 设置右上角按钮显示
     *
     * @param btnText 按钮显示内容
     */
    public void setRightBtn(String btnText) {
        Button btnRight = (Button) findViewById(R.id.btnRight);
        if (btnRight == null) {
            return;
        }
        btnRight.setText(btnText);
        btnRight.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右上角按钮显示
     *
     * @param btnTextId 按钮显示内容对应的字符串ID
     */
    public void setRightBtn(int btnTextId) {
        Button btnRight = (Button) findViewById(R.id.btnRight);
        if (btnRight == null) {
            return;
        }
        btnRight.setText(btnTextId);
        btnRight.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右上角按钮显示
     *
     * @param btnText  按钮显示内容
     * @param listener 按钮点击事件
     */
    public void setRightBtn(String btnText, View.OnClickListener listener) {
        Button btnRight = (Button) findViewById(R.id.btnRight);
        if (btnRight == null) {
            return;
        }
        btnRight.setText(btnText);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setOnClickListener(listener);
    }

    /**
     * 设置右上角按钮显示
     *
     * @param btnTextId 按钮显示内容对应的字符串ID
     * @param listener  按钮点击事件
     */
    public void setRightBtn(int btnTextId, View.OnClickListener listener) {
        Button btnRight = (Button) findViewById(R.id.btnRight);
        if (btnRight == null) {
            return;
        }
        btnRight.setText(btnTextId);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setOnClickListener(listener);
    }


    /**
     * 设置右上角按钮显示
     *
     * @param btnTextId 按钮显示内容对应的字符串ID
     * @param listener  按钮点击事件
     */
    public void setSubmitBtn(int btnTextId, View.OnClickListener listener) {
        Button btnRight = (Button) findViewById(R.id.btnOneSubmit);
        if (btnRight == null) {
            return;
        }
        btnRight.setText(btnTextId);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setOnClickListener(listener);
    }

    /**
     * 设置右上角图标按钮显示
     *
     * @param resourcesID 图标背景资源
     * @param listener    按钮点击事件
     */
    public void setRightImg(int resourcesID, View.OnClickListener listener) {
        ImageView ivRight = (ImageView) findViewById(R.id.imgFilter);
        if (ivRight == null) {
            return;
        }
        ivRight.setImageResource(resourcesID);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setOnClickListener(listener);
    }

    public int getHeadResourcesID() {
        return headResourcesID;
    }

    public void setHeadResourcesID(int headResourcesID) {
        this.headResourcesID = headResourcesID;
    }
}
