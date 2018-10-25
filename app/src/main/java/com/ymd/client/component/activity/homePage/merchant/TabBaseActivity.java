package com.ymd.client.component.activity.homePage.merchant;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dalong on 2017/1/12.
 */

public class TabBaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }


    protected void setStatusBar() {

    }


    /**
     * 左上角返回按钮
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }
}
