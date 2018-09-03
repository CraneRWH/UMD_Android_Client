package com.ymd.client.component.activity.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ymd.client.R;

/**
 * 作者:rongweihe
 * 日期:2018/8/22  时间:22:56
 * 描述:  启动页
 * 修改历史:
 */
public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        LoginActivity.startAction(this);
    }
}
