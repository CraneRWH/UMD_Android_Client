package com.ymd.client.component.activity.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.ymd.client.R;
import com.ymd.client.common.base.service.LocationIntentService;
import com.ymd.client.component.activity.main.MainActivity;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.DataUtils;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

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
        View view = findViewById(R.id.splash_parent);
        AlphaAnimation animation = new AlphaAnimation(0.8f, 1.0f);
        animation.setDuration(2000);
        view.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                requestCities();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        setService();
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

    private void requestCities() {

        CommonShared.setString(LocationInfo.CITYS_INFO_SETTING, DataUtils.getCityList());
        LocationInfo.getInstance().refreshCitiesData();
        toMainActivity();
    //    toLoginHandler.sendEmptyMessage(0);
        /*WebUtil.getInstance().requestPOST(this, URLConstant.QUERY_CITY_DATA, null,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        CommonShared.setString(LocationInfo.CITYS_INFO_SETTING, result.optString("list"));
                        LocationInfo.getInstance().refreshCitiesData();
                        toLoginHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });*/
    }
    private void toMainActivity() {
    //    LoginActivity.startAction(this);
        MainActivity.startAction(this);
        finish();
    }


    private void setService() {
        // 注册广播
     /*   IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.LOCATION_ACTION);
        LogoActivity.this.registerReceiver(new LocationBroadcastReceiver(),
                filter);*/

        // 启动服务
        LocationIntentService.startAction(this);
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
