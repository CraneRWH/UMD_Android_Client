/**
 * 
 */
package com.ymd.client.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备信息类
 * 需在AndroidManifest.xml中开放相应权限，方可正常使用
 * @author wangyuan
 *
 */
public class DeviceUtil {
    /**
     * 获取设备id
     * @return
     */
    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if(deviceId == null || deviceId.equals("")){
            @SuppressWarnings("deprecation")
            String androidId = Settings.System.getString(context.getContentResolver(),
                    Settings.System.ANDROID_ID);
            return androidId;
        }
        return deviceId;
    }

    /**
     * 设备型号
     * @return
     */
    public static String getDeviceType(){
        return android.os.Build.MODEL;
    }

    /**
     * 设备OS版本号
     * @return
     */
    public static String getOSVersion(){
        return "android " + android.os.Build.VERSION.RELEASE;
    }

    /**
     * 手机号
     * @return
     */
    public static String getPhoneNum(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * 运营商信息
     * @return
     */
    public static String getProvidersInfo(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimOperatorName();
    }
    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getWidth(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    /**
     * 获取屏幕高度
     * @return
     */
    public static int getHeight(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /*
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     *
     * @param packageName：应用包名
     *
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
