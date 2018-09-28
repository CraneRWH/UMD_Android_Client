package com.ymd.client.component.activity.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.ymd.client.R;
import com.ymd.client.common.base.service.LocationIntentService;
import com.ymd.client.component.activity.main.MainActivity;
import com.ymd.client.component.event.LocationEvent;
import com.ymd.client.component.event.LoginEvent;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.DataUtils;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.updateApp.UpdateAppUtil;
import com.ymd.client.web.WebUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
            //    checkUpdate();

            //    setService();
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    //    requestCities();
        setService();
    }

    private void checkUpdate() {
        UpdateAppUtil update = new UpdateAppUtil(this, mHandler);
        update.checkUpdate();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            requestCities();
        };
    };
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
   /*     IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.LOCATION_ACTION);
        LogoActivity.this.registerReceiver(new LocationBroadcastReceiver(),
                filter);
*/
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
            toMainActivity();
        }
    }
/*

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocationEvent event) {
        if (event.isLocation()) {
            toMainActivity();
        } else {
        //    ToastUtil.ToastMessage(LogoActivity.this, "定位失败");
            toMainActivity();
        }
    }*/


    private static final int LOCATION_CODE = 1;
    private LocationManager lm;//【位置管理】

    public void quanxian(){
        lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("BRG","没有权限");
                // 没有权限，申请权限。
                // 申请授权。
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
//                        Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();

            } else {

        //        startLocation();
                // 有权限了，去放肆吧。
//                        Toast.makeText(getActivity(), "有权限", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("BRG","系统检测到未开启GPS定位服务");
            Toast.makeText(this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。

         //           startLocation();
                } else {
                    // 权限被用户拒绝了。
                    Toast.makeText(this, "定位权限被禁止，相关地图功能无法使用！",Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}
