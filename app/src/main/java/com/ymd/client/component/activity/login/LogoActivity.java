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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.ymd.client.R;
import com.ymd.client.common.base.service.LocationIntentService;
import com.ymd.client.component.activity.main.MainActivity;
import com.ymd.client.component.event.LocationEvent;
import com.ymd.client.component.event.LoginEvent;
import com.ymd.client.model.bean.city.LocationInfoEntity;
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
                quanxian();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            //    checkUpdate();

            //    setService();
            //    mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    //    requestCities();
    //    setService();
        initLocation();
    //    startLocation();
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

                startLocation();
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

                    startLocation();
                } else {
                    // 权限被用户拒绝了。
                    Toast.makeText(this, "定位权限被禁止，相关地图功能无法使用！",Toast.LENGTH_LONG).show();
                    toMainActivity();
                }

            }
        }
    }



    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                LocationInfoEntity locationInfoEntity = new LocationInfoEntity();
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");

                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    locationInfoEntity.setLongitude(location.getLongitude());
                    locationInfoEntity.setLatitude(location.getLatitude());
                    locationInfoEntity.setAccuracy(location.getAccuracy());
                    locationInfoEntity.setSpeed(location.getSpeed());
                    locationInfoEntity.setCountry(locationInfoEntity.getCountry());
                    locationInfoEntity.setProvince(location.getProvince());
                    locationInfoEntity.setCity(location.getCity());
                    locationInfoEntity.setCityCode(location.getCityCode());
                    locationInfoEntity.setAdCode(location.getAdCode());
                    locationInfoEntity.setAddress(location.getAddress());
                    LocationInfo.getInstance().setLocationInfo(locationInfoEntity);
                    toMainActivity();
                    //定位完成的时间
                    //    sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                    toMainActivity();
                }
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                sb.append("****************").append("\n");
                //定位之后的回调时间
                //    sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = sb.toString();
                stopLocation();
                //    tvResult.setText(result);
            } else {
                //    tvResult.setText("定位失败，loc is null");
            }
        }
    };


    /**
     * 获取GPS状态的字符串
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode){
        String str = "";
        switch (statusCode){
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }


    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        //根据控件的选择，重新设置定位参数
        //    resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}
