package com.ymd.client.component.activity.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.ymd.client.R;
import com.ymd.client.common.base.service.AndroidLocationService;
import com.ymd.client.component.activity.main.MainActivity;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.model.info.LocationInfo;

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
    //    setService();
        toLoginHandler.sendEmptyMessage(0);
    }

    private Handler toLoginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //两个参数分别表示进入的动画,退出的动画
        //    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            try {
                /**
                 * miaoyan 要求 停1.5秒 看logo
                 */
                Thread.sleep(3000);
                toMainActivity();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };

    private void toMainActivity() {
        LoginActivity.startAction(this);
    //        startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
    }


    private void setService() {
        // 注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.LOCATION_ACTION);
        LogoActivity.this.registerReceiver(new LocationBroadcastReceiver(),
                filter);

        // 启动服务
        Intent intent = new Intent();
        intent.setClass(LogoActivity.this, AndroidLocationService.class);
        startService(intent);
        LocationInfo.initInstance(getApplicationContext());
    //    init();
    }

    private class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(Constants.LOCATION_ACTION)) {
                return;
            }
            String latlong = intent.getStringExtra("NAME");
            unregisterReceiver(this);// 不需要时注销
        }
    }

}
